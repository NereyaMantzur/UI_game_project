package com.dev.nereya.ui_game_project.model

import com.dev.nereya.ui_game_project.utils.AsteroidState

class GameManager {

    var isGameEnded: Boolean = false
    var score: Int = 0

    var currentShipIndex: Int = 2

    var hits: Int = 0
    var hearts: Int = 3

    fun checkCollision(state: AsteroidState, shipColumnIndex: Int): Boolean {
        if (state.rowIndex == 4 && state.colIndex == shipColumnIndex) {
            hearts--
            hits++

            // Check for Game Over
            if (hearts <= 0) {
                isGameEnded = true
            }
            return true
        }
        return false
    }
}