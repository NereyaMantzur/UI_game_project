package com.dev.nereya.ui_game_project.utils

data class Coin(var colIndex: Int, var rowIndex: Int) {

    fun moveForward() {
        rowIndex++
        // Reset if it goes off screen
        if (rowIndex > 4) {
            colIndex = (0..4).random()
            // Spawn it further up so they don't all appear at once
            rowIndex = (-10..-1).random()
        }
    }
}
