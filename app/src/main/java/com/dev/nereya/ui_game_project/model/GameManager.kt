package com.dev.nereya.ui_game_project.model

import com.dev.nereya.ui_game_project.utils.AsteroidState

class GameManager() {

    var isGameEnded: Boolean = false
    var buttonClicked: Boolean = true
    var score: Int = 0
    var currentShipIndex: Int = 0
    var hits: Int = 0
    var hearts = 3

    fun checkCollision(
        state: AsteroidState,
        shipIndex: Int
    ): Boolean {
        val shipRowIndex = state.rowStart / 5
        if (state.colIndex == 4 && shipRowIndex == shipIndex) {
            hearts--
            hits++
            if (hearts == 0) {
                isGameEnded = true
            }
            return true
        }
        return false
    }
}