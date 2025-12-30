package com.dev.nereya.ui_game_project.model

data class Player private constructor(
    var playerName: String ,
    val playerScore : String
){

    class builder(
        var playerName: String ,
        var playerScore : String
    ){
        fun playerName(playerName:String ) = apply { this.playerName = playerName }
        fun playerScore(playerScore:String ) = apply { this.playerScore = playerScore }
        fun build() = Player(
            playerName,
            playerScore
        )
    }

}
