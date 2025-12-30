package com.dev.nereya.ui_game_project.model

data class LeaderBoardList private constructor(
    val leaderBoard: List<Player>
) {
    class Builder(
        var leaderBoard: List<Player> = mutableListOf()
    ) {
        fun addPlayer(player: Player) = apply { (this.leaderBoard as MutableList).add(player)
            leaderBoard.sortedByDescending { it.playerScore }
            leaderBoard = leaderBoard.subList(0,9)
        }


        fun build() = LeaderBoardList(leaderBoard)
    }
}
