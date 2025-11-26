package model

import android.R
import androidx.appcompat.widget.AppCompatImageView

class GameManager(spaceships: Int = 3) {

    var isGameEnded: Boolean = false
    var score: Int = 0
    var buttonClicked : Boolean = true
    var currentShipIndex:Int = 0
}