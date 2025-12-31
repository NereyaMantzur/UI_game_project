package com.dev.nereya.ui_game_project

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class MenuActivity : AppCompatActivity() {
    private lateinit var menu_BTN_slow: MaterialButton
    private lateinit var menu_BTN_fast: MaterialButton
    private lateinit var menu_BTN_sensor: MaterialButton
    private lateinit var menu_BTN_leaderboard: MaterialButton
    private lateinit var menu_container: AppCompatImageView


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
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
        menu_BTN_slow.setOnClickListener { changeActivity(MainActivity::class.java, "slow") }
        menu_BTN_fast.setOnClickListener { changeActivity(MainActivity::class.java, "fast") }
        menu_BTN_sensor.setOnClickListener { changeActivity(MainActivity::class.java, "sensor") }
        menu_BTN_leaderboard.setOnClickListener {
            changeActivity(
                ScoreActivity::class.java,
                "leaderboard"
            )
        }
    }


    private fun <T> changeActivity(activityClass: Class<T>, mode: String? = null) {
        val intent = Intent(this, activityClass)

        mode?.let {
            intent.putExtra("GAME_MODE", it)
        }

        startActivity(intent)
        finish()
    }
}

