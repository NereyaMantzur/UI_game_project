package com.dev.nereya.ui_game_project.model

data class Player private constructor(
    val playerName: String ,
    val playerScore : String,
    val location: String
){

    class builder(
        var playerName: String ,
        var playerScore : String,
        var location: String
    ){
        fun playerName(playerName:String ) = apply { this.playerName = playerName }
        fun playerScore(playerScore:String ) = apply { this.playerScore = playerScore }
        fun location(location:String ) = apply { this.location = location }
        fun build() = Player(
            playerName,
            playerScore
            ,location

        )
    }

}
