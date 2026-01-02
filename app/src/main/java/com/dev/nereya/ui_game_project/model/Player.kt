package com.dev.nereya.ui_game_project.model

data class Player(
    val playerName: String ,
    val playerScore : Int,
    val lat: Double,
    val lon: Double
){

    class builder(
        var playerName: String  = "",
        var playerScore : Int =0,
        var lat: Double =0.0,
        var lon: Double =0.0
    ){
        fun playerName(playerName:String ) = apply { this.playerName = playerName }
        fun playerScore(playerScore: Int ) = apply { this.playerScore = playerScore }
        fun lat(lat:Double ) = apply { this.lat = lat }
        fun lon(lon:Double ) = apply { this.lon = lon }
        fun build() = Player(
            playerName,
            playerScore,
            lat,
            lon
        )
    }

}
