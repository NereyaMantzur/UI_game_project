package com.dev.nereya.ui_game_project

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import model.GameManager


class MainActivity : AppCompatActivity() {
    private lateinit var main_BG_pic: AppCompatImageView
    private lateinit var main_FAB_left: ExtendedFloatingActionButton
    private lateinit var main_FAB_right: ExtendedFloatingActionButton

    private lateinit var main_spaceships: Array<AppCompatImageView>
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
    }

    fun findViews() {
        main_BG_pic = findViewById(R.id.main_BG_pic)
        main_FAB_left = findViewById(R.id.main_FAB_left)
        main_FAB_right = findViewById(R.id.main_FAB_right)
        main_spaceships = arrayOf(
            findViewById(R.id.main_spaceship0),
            findViewById(R.id.main_spaceship1),
            findViewById(R.id.main_spaceship2),
            findViewById(R.id.main_spaceship3),
            findViewById(R.id.main_spaceship4)
        )
    }


    fun initViews() {
        Glide.with(this)
            .load("https://cdn.pixabay.com/animation/2023/07/13/14/22/14-22-36-485_512.gif")
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(main_BG_pic)

        main_FAB_right.setOnClickListener {
            gameManager.buttonClicked = true
            refreshUI()
        }

        main_FAB_left.setOnClickListener {
            gameManager.buttonClicked = false
            refreshUI()
        }

        // UNCOMMENT THIS: ensure the correct ship is shown when app starts
        refreshUI()
    }

    fun refreshUI() {
        if (gameManager.isGameEnded) {
            changeActivity(gameManager.score)
            return
        }

        main_spaceships[gameManager.currentShipIndex].visibility = View.INVISIBLE

        if (gameManager.buttonClicked) { // Moving Right
            if (gameManager.currentShipIndex < 2) {
                gameManager.currentShipIndex++
            }
        } else { // Moving Left
            if (gameManager.currentShipIndex > 0) {
                gameManager.currentShipIndex--
            }
        }
        main_spaceships[gameManager.currentShipIndex].visibility = View.VISIBLE
    }
    fun changeActivity(score: Int) {
        val intent = android.content.Intent(this, ScoreActivity::class.java)
        intent.putExtra("score", score)
        startActivity(intent)
        finish()
    }
}



