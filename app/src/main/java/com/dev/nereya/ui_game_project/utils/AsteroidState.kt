package com.dev.nereya.ui_game_project.utils

data class AsteroidState(var colIndex: Int, var rowIndex: Int) {

    fun moveForward() {
        rowIndex++

        if (rowIndex > 4) {
            colIndex = (0..4).random()
            rowIndex = (-4..-1).random()
        }
    }
}
