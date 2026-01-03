package com.dev.nereya.ui_game_project.model

import com.dev.nereya.ui_game_project.utils.AsteroidState

class GameManager {

    var isGameEnded: Boolean = false
    var score: Int = 0
    var currentShipIndex: Int = 2
    var hits: Int = 0
    var hearts: Int = 3

    val asteroids: Array<AsteroidState> = arrayOf(
        AsteroidState((0..4).random(), -1),
        AsteroidState((0..4).random(), -4),
        AsteroidState((0..4).random(), -3),
        AsteroidState((0..4).random(), -2),
    )

    fun moveShip(direction: Int) {
        if (direction == 1 && currentShipIndex < 4) {
            currentShipIndex++
        } else if (direction == -1 && currentShipIndex > 0) {
            currentShipIndex--
        }
    }

    fun moveAsteroids() {
        for (asteroid in asteroids) {
            asteroid.moveForward()
        }
    }

    fun checkForCollisions(): Boolean {
        var crashFound = false
        for (asteroid in asteroids) {
            if (asteroid.rowIndex == 4 && asteroid.colIndex == currentShipIndex) {
                hearts--
                hits++
                crashFound = true

                if (hearts <= 0) {
                    isGameEnded = true
                }
            }
        }
        return crashFound
    }
}