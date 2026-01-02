package com.dev.nereya.ui_game_project

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dev.nereya.ui_game_project.model.GameManager
import com.dev.nereya.ui_game_project.model.LeaderBoardList
import com.dev.nereya.ui_game_project.model.Player
import com.dev.nereya.ui_game_project.utils.AsteroidState
import com.dev.nereya.ui_game_project.utils.Constants
import com.dev.nereya.ui_game_project.utils.ImageLoader
import com.dev.nereya.ui_game_project.utils.SharedPreferencesManager
import com.dev.nereya.ui_game_project.utils.SignalManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var main_BG_pic: AppCompatImageView
    private lateinit var main_hearts: Array<AppCompatImageView>
    private lateinit var main_FAB_left: ExtendedFloatingActionButton
    private lateinit var main_FAB_right: ExtendedFloatingActionButton
    private lateinit var main_score: MaterialTextView

    private lateinit var main_spaceships: Array<AppCompatImageView>
    private lateinit var main_asteroidsMatrix: Array<Array<AppCompatImageView>>
    private lateinit var gameMode: String

    private lateinit var main_LBL_score: MaterialTextView
    private lateinit var main_BTN_save: MaterialButton
    private lateinit var main_container: LinearLayoutCompat
    private lateinit var main_name_fill: TextInputLayout
    private var leaderBoard: LeaderBoardList = LeaderBoardList()
    private val asteroids: Array<AsteroidState> = arrayOf(
        AsteroidState((0..4).random(), -1),
        AsteroidState((0..4).random(), -4),
        AsteroidState((0..4).random(), -3),
        AsteroidState((0..4).random(), -2),
    )
    private var gameManager: GameManager = GameManager()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val handler: Handler = Handler(Looper.getMainLooper())
    private val runnable: Runnable = object : Runnable {
        override fun run() {
            gameManager.score += 10
            updateScoreUI()
            moveAsteroidsLogic(asteroids)
            var crashed = false

            for (asteroid in asteroids) {
                if (gameManager.checkCollision(asteroid, gameManager.currentShipIndex)) {
                    crashed = true
                }
            }

            if (crashed) {
                SignalManager.getInstance().vibrate()
                SignalManager.getInstance().toast("OUCH")
                updateHeartsUI()
            }
            if (gameManager.isGameEnded) {
                handler.removeCallbacks(this)
                main_container.visibility = View.VISIBLE
                main_LBL_score.text = gameManager.score.toString()
                SignalManager.getInstance().toast("GAME OVER!")
            } else {
                if (gameMode == Constants.GameMode.SLOW)
                    handler.postDelayed(this, Constants.Timer.DELAY_SLOW)
                else if (gameMode == Constants.GameMode.FAST)
                    handler.postDelayed(this, Constants.Timer.DELAY_FAST)
                else(
                    handler.postDelayed(this, Constants.Timer.DELAY_SLOW)
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        gameMode = intent.getStringExtra("GameMode") ?: "fast"
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        loadLeaderBoard()
        findViews()
        initViews()
        handler.postDelayed(runnable, Constants.Timer.DELAY_FAST)
    }
    private fun findViews() {
        main_LBL_score = findViewById(R.id.main_container_score)
        main_BTN_save = findViewById(R.id.main_BTN_save)
        main_container = findViewById(R.id.main_container)
        main_name_fill = findViewById(R.id.main_name_fill)
        main_BG_pic = findViewById(R.id.main_BG_pic)
        main_FAB_left = findViewById(R.id.main_FAB_left)
        main_FAB_right = findViewById(R.id.main_FAB_right)
        main_score = findViewById(R.id.main_score)

        main_hearts = arrayOf(
            findViewById(R.id.main_IMG_heart0),
            findViewById(R.id.main_IMG_heart1),
            findViewById(R.id.main_IMG_heart2)
        )
        main_asteroidsMatrix = Array(5) { col ->
            Array(5) { row ->
                val idStr = "main_asteroid${col}_${row}"
                val resId = resources.getIdentifier(idStr, "id", packageName)
                findViewById(resId)
            }
        }

        main_spaceships = Array(5) { col ->
            val idStr = "main_spaceship${col}_4"
            val resId = resources.getIdentifier(idStr, "id", packageName)
            findViewById(resId)
        }
    }
    private fun initViews() {
        ImageLoader.getInstance().loadImage(
            "https://cdn.pixabay.com/animation/2023/07/13/14/22/14-22-36-485_512.gif",
            main_BG_pic
        )

        main_FAB_right.setOnClickListener {
            moveShip(1)
        }
        main_FAB_left.setOnClickListener {
            moveShip(-1)
        }

        main_score.text = gameManager.score.toString()
        main_BTN_save.setOnClickListener {
            val name = main_name_fill.editText?.text.toString()

            if (name.isNotEmpty()) {
                saveScoreWithLocation()
            } else {
                main_name_fill.error = "Please enter your name"
            }
        }
        refreshShipUI()
    }

    private fun moveShip(direction: Int) {
        if (gameManager.isGameEnded) return

        main_spaceships[gameManager.currentShipIndex].visibility = View.INVISIBLE

        if (direction == 1 && gameManager.currentShipIndex < 4) {
            gameManager.currentShipIndex++
        } else if (direction == -1 && gameManager.currentShipIndex > 0) {
            gameManager.currentShipIndex--
        }

        main_spaceships[gameManager.currentShipIndex].visibility = View.VISIBLE
    }
    private fun refreshShipUI() {
        for (ship in main_spaceships) {
            ship.visibility = View.INVISIBLE
        }
        main_spaceships[gameManager.currentShipIndex].visibility = View.VISIBLE
    }
    private fun moveAsteroidsLogic(states: Array<AsteroidState>) {
        for (state in states) {
            if (state.rowIndex in 0..4 && state.colIndex in 0..4) {
                main_asteroidsMatrix[state.colIndex][state.rowIndex].visibility = View.INVISIBLE
            }
        }

        for (state in states) {
            state.moveForward()
        }

        for (state in states) {
            if (state.rowIndex in 0..4 && state.colIndex in 0..4) {
                main_asteroidsMatrix[state.colIndex][state.rowIndex].visibility = View.VISIBLE
            }
        }
    }
    fun updateHeartsUI() {
        for (i in main_hearts.indices) {
            main_hearts[i].visibility = if (i < gameManager.hearts) View.VISIBLE else View.INVISIBLE
        }
    }
    private fun updateScoreUI() {
        main_score.text = gameManager.score.toString()
    }

    fun changeActivity() {
        val intent = Intent(this, ScoreActivity::class.java)
        intent.putExtra("SCORE", main_score.text)
        startActivity(intent)
        finish()
    }

    private fun saveScoreWithLocation() {
        val fineStatus = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
        val coarseStatus = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)

        if (fineStatus != PackageManager.PERMISSION_GRANTED && coarseStatus != PackageManager.PERMISSION_GRANTED) {
            savePlayerToLeaderboard(0.0, 0.0)
            return
        }

        val cancellationToken = com.google.android.gms.tasks.CancellationTokenSource()
        fusedLocationClient.getCurrentLocation(
            com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY,
            cancellationToken.token
        ).addOnSuccessListener { location ->
            if (location != null) {
                savePlayerToLeaderboard(location.latitude, location.longitude)
            } else {
                savePlayerToLeaderboard(0.0, 0.0)
            }
        }.addOnFailureListener {
            savePlayerToLeaderboard(0.0, 0.0)
        }
    }

    private fun savePlayerToLeaderboard(lat: Double, lon: Double) {
        val name = main_name_fill.editText?.text.toString()
        val score = gameManager.score
        val player = Player(
            name,
            score,
            lat,
            lon
        )
        leaderBoard.add(player)
        val gson = Gson()
        SharedPreferencesManager.getInstance().putString(Constants.SPKeys.LEADERBOARD_KEY, gson.toJson(leaderBoard))
        changeActivity()
    }

    private fun loadLeaderBoard() {
        val gson = Gson()
        val json = SharedPreferencesManager.getInstance().getString(Constants.SPKeys.LEADERBOARD_KEY, "")
        if (json.isNotEmpty()) {
            leaderBoard = gson.fromJson(json, LeaderBoardList::class.java)
        }
    }
}