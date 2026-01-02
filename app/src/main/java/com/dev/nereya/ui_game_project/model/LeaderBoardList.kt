package com.dev.nereya.ui_game_project.model

data class LeaderBoardList(
    var leaderBoard: List<Player> = emptyList()
) {
    fun add(player: Player) {
        val mutableList = leaderBoard.toMutableList()
        mutableList.add(player)

        leaderBoard = mutableList.sortedByDescending { it.playerScore }.take(10)
    }
}
