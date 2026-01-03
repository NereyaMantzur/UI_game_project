package com.dev.nereya.ui_game_project

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dev.nereya.ui_game_project.utils.Constants
import com.dev.nereya.ui_game_project.utils.SignalManager
import com.google.android.material.button.MaterialButton

class MenuActivity : AppCompatActivity() {
    private lateinit var menu_BTN_slow: MaterialButton
    private lateinit var menu_BTN_fast: MaterialButton
    private lateinit var menu_BTN_sensor: MaterialButton
    private lateinit var menu_BTN_leaderboard: MaterialButton
    private lateinit var menu_container: AppCompatImageView

    private var selectedMode: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        permissionCheck()
        findViews()
        initViews()
    }

    private fun findViews() {
        menu_BTN_slow = findViewById(R.id.menu_BTN_slow)
        menu_BTN_fast = findViewById(R.id.menu_BTN_fast)
        menu_BTN_sensor = findViewById(R.id.menu_BTN_sensor)
        menu_BTN_leaderboard = findViewById(R.id.menu_BTN_leaderboard)
        menu_container = findViewById(R.id.menu_container)
    }

    private fun initViews() {
        menu_BTN_slow.setOnClickListener {
            selectedMode = Constants.GameMode.SLOW
            moveToGame() }
        menu_BTN_fast.setOnClickListener {
            selectedMode = Constants.GameMode.FAST
            moveToGame() }
        menu_BTN_sensor.setOnClickListener { selectedMode = Constants.GameMode.SENSOR
            moveToGame() }

        menu_BTN_leaderboard.setOnClickListener {
            changeActivity(ScoreActivity::class.java, "leaderboard")
        }
    }

    private fun permissionCheck() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            moveToGame()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION),
                101
            )
        }
    }

    private fun moveToGame() {
        selectedMode?.let {
            changeActivity(MainActivity::class.java, it)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 101) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                SignalManager.getInstance().toast("Location Permission Granted!")
            } else {
                SignalManager.getInstance().toast("Permission Denied - GPS will not be saved.")
            }
            moveToGame()
        }
    }

    private fun <T> changeActivity(activityClass: Class<T>, mode: String? = null) {
        val intent = Intent(this, activityClass)
        mode?.let {
            intent.putExtra("GameMode", it)
        }
        startActivity(intent)
    }
}
