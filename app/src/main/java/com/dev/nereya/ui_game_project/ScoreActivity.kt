package com.dev.nereya.ui_game_project

import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dev.nereya.ui_game_project.interfaces.CallbackHighScoreClicked
import com.dev.nereya.ui_game_project.ui.HighScoreFragment
import com.dev.nereya.ui_game_project.ui.MapFragment
import com.dev.nereya.ui_game_project.utils.ImageLoader


class ScoreActivity : AppCompatActivity() {

    private lateinit var main_FRAME_list: FrameLayout
    private lateinit var main_FRAME_map: FrameLayout
    private lateinit var mapFragment: MapFragment
    private lateinit var highScoreFragment: HighScoreFragment
    private lateinit var score_BG_img: AppCompatImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_score)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViews()
        initViews()
    }

    private fun findViews() {
        main_FRAME_list = findViewById(R.id.score_FRAME_list)
        main_FRAME_map = findViewById(R.id.score_FRAME_map)
        score_BG_img = findViewById(R.id.score_BG_img)
    }

    private fun initViews() {
        ImageLoader.getInstance().loadImage(
            "https://cdn.pixabay.com/animation/2023/07/13/14/22/14-22-36-485_512.gif",
            score_BG_img
        )
        mapFragment = MapFragment()
        highScoreFragment = HighScoreFragment()

        HighScoreFragment.highScoreItemClicked = object : CallbackHighScoreClicked {
            override fun highScoreItemClicked(lat: Double, lon: Double) {
                mapFragment.zoom(lat, lon)
            }
        }

        supportFragmentManager.beginTransaction()
            .add(R.id.score_FRAME_map, mapFragment)
            .commit()

        supportFragmentManager.beginTransaction()
            .add(R.id.score_FRAME_list, highScoreFragment)
            .commit()
    }
}