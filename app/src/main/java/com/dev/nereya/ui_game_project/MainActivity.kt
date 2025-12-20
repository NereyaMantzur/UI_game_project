package com.dev.nereya.ui_game_project

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.dev.nereya.ui_game_project.utils.Constants
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.dev.nereya.ui_game_project.model.GameManager
import com.dev.nereya.ui_game_project.utils.AsteroidState
import com.dev.nereya.ui_game_project.utils.SignalManager
import com.google.android.material.textview.MaterialTextView
import kotlin.jvm.java


class MainActivity : AppCompatActivity() {
    private lateinit var main_BG_pic: AppCompatImageView
    private lateinit var main_hearts: Array<AppCompatImageView>
    private lateinit var main_FAB_left: ExtendedFloatingActionButton
    private lateinit var main_FAB_right: ExtendedFloatingActionButton
    private lateinit var main_spaceships: Array<AppCompatImageView>
    private lateinit var main_asteroids: Array<AppCompatImageView>

    private lateinit var main_score : MaterialTextView
    private val handler: Handler = Handler(Looper.getMainLooper())
    val asteroids: Array<AsteroidState> = arrayOf(
        AsteroidState(0, (-3..-1).random()),
        AsteroidState(5, (-3..-1).random())
    )

    val runnable: Runnable = object : Runnable {
        override fun run() {
            gameManager.score += 10
            updateScoreUI()
            moveAsteroids(asteroids)
            val hit1 = gameManager.checkCollision(asteroids[0], gameManager.currentShipIndex)
            val hit2 = gameManager.checkCollision(asteroids[1], gameManager.currentShipIndex)

            if (hit1 || hit2) {
                SignalManager.getInstance().vibrate()
                SignalManager.getInstance().toast("OUCH")
                updateHeartsUI()
            }
            if (gameManager.isGameEnded) {
                handler.removeCallbacks(this)
                SignalManager.getInstance().toast("GAME OVER")
                changeActivity()
            } else {
                handler.postDelayed(this, Constants.Timer.DELAY)
            }
        }

        private fun moveAsteroids(states: Array<AsteroidState>) {
            for (state in states) {
                if (state.colIndex in 0..4) {
                    main_asteroids[state.currentPosition].visibility = View.INVISIBLE
                }
            }
            for (state in states) {
                state.moveForward()
            }
            for (i in states.indices) {
                for (j in i + 1 until states.size) {
                    if (states[i].currentPosition == states[j].currentPosition) {
                        states[i].colIndex++
                    }
                }
            }
            for (state in states) {
                if (state.colIndex in 0..4) {
                    main_asteroids[state.currentPosition].visibility = View.VISIBLE
                }
            }
        }

    }

    var gameManager: GameManager = GameManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViews()
        initViews()
        handler.postDelayed(runnable, Constants.Timer.DELAY)
    }

    fun findViews() {
        main_BG_pic = findViewById(R.id.main_BG_pic)
        main_FAB_left = findViewById(R.id.main_FAB_left)
        main_FAB_right = findViewById(R.id.main_FAB_right)
        main_score = findViewById(R.id.main_score)
        main_hearts = arrayOf(
            findViewById(R.id.main_IMG_heart0),
            findViewById(R.id.main_IMG_heart1),
            findViewById(R.id.main_IMG_heart2)
        )
        main_spaceships = arrayOf(
            findViewById(R.id.main_spaceship0),
            findViewById(R.id.main_spaceship1),
            findViewById(R.id.main_spaceship2)
        )
        main_asteroids = arrayOf(
            findViewById(R.id.main_asteroid0_0),
            findViewById(R.id.main_asteroid0_1),
            findViewById(R.id.main_asteroid0_2),
            findViewById(R.id.main_asteroid0_3),
            findViewById(R.id.main_asteroid0_4),
            findViewById(R.id.main_asteroid1_0),
            findViewById(R.id.main_asteroid1_1),
            findViewById(R.id.main_asteroid1_2),
            findViewById(R.id.main_asteroid1_3),
            findViewById(R.id.main_asteroid1_4),
            findViewById(R.id.main_asteroid2_0),
            findViewById(R.id.main_asteroid2_1),
            findViewById(R.id.main_asteroid2_2),
            findViewById(R.id.main_asteroid2_3),
            findViewById(R.id.main_asteroid2_4)
        )
    }

    fun initViews() {
        Glide.with(this)
            .load("https://cdn.pixabay.com/animation/2023/07/13/14/22/14-22-36-485_512.gif")
            .centerCrop()
            .placeholder(findViewById<AppCompatImageView>(R.id.main_BG_pic).drawable)
            .into(main_BG_pic)

        main_FAB_right.setOnClickListener {
            gameManager.buttonClicked = true
            refreshUI()
        }
        main_FAB_left.setOnClickListener {
            gameManager.buttonClicked = false
            refreshUI()
        }
        main_score.text = gameManager.score.toString()
        refreshUI()
    }

    fun refreshUI() {
        if (gameManager.isGameEnded) {
            changeActivity()
            return
        }
        main_spaceships[gameManager.currentShipIndex].visibility = View.INVISIBLE

        if (gameManager.buttonClicked) {
            if (gameManager.currentShipIndex < 2) {
                gameManager.currentShipIndex++
            }
        } else {
            if (gameManager.currentShipIndex > 0) {
                gameManager.currentShipIndex--
            }
        }
        main_spaceships[gameManager.currentShipIndex].visibility = View.VISIBLE
    }

    fun updateHeartsUI() {
        for (i in main_hearts.indices) {
            if (i < gameManager.hearts) {
                main_hearts[i].visibility = View.VISIBLE
            } else {
                main_hearts[i].visibility = View.INVISIBLE
            }
        }
    }

    private fun updateScoreUI() {
        main_score.text = gameManager.score.toString()
    }

    fun changeActivity() {
        val intent = android.content.Intent(this, ScoreActivity::class.java)
        startActivity(intent)
        finish()
    }
}



