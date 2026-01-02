package com.dev.nereya.ui_game_project.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev.nereya.ui_game_project.R
import com.dev.nereya.ui_game_project.interfaces.CallbackHighScoreClicked
import com.dev.nereya.ui_game_project.model.LeaderBoardList
import com.dev.nereya.ui_game_project.utils.Constants
import com.dev.nereya.ui_game_project.utils.SharedPreferencesManager
import com.google.android.material.textview.MaterialTextView
import com.google.gson.Gson


class HighScoreFragment : Fragment() {

    private var highScoreList: Array<MaterialTextView>? = null
    private lateinit var LeaderBoard: LeaderBoardList

    companion object {
        var highScoreItemClicked: CallbackHighScoreClicked? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v: View = inflater.inflate(R.layout.fragment_high_score, container, false)
        loadLeaderboard()

        findViews(v)
        initViews()
        return v
    }

    private fun loadLeaderboard() {
        val gson = Gson()
        val leaderBoardFromSP: String = SharedPreferencesManager
            .getInstance()
            .getString(Constants.SPKeys.LEADERBOARD_KEY, "")

        LeaderBoard = if (leaderBoardFromSP.isEmpty()) {
            LeaderBoardList()
        } else {
            gson.fromJson(leaderBoardFromSP, LeaderBoardList::class.java) ?: LeaderBoardList()
        }
    }

    private fun initViews() {
        val dataList = LeaderBoard.leaderBoard.sortedByDescending {
            it.playerScore
        }

        for (i in 0..9) {
            if (i < dataList.size) {
                val player = dataList[i]

                highScoreList?.get(i)?.apply {
                    visibility = View.VISIBLE
                    text = "${player.playerName}\t\t\t${player.playerScore}"

                    setOnClickListener {
                        val lat = player.lat
                        val lon = player.lon

                        highScoreItemClicked?.highScoreItemClicked(lat, lon)
                    }
                }
            } else {
                highScoreList?.get(i)?.visibility = View.INVISIBLE
            }
        }
    }

    private fun findViews(v: View) {
        highScoreList = arrayOf(
            v.findViewById(R.id.highScore_text_1),
            v.findViewById(R.id.highScore_text_2),
            v.findViewById(R.id.highScore_text_3),
            v.findViewById(R.id.highScore_text_4),
            v.findViewById(R.id.highScore_text_5),
            v.findViewById(R.id.highScore_text_6),
            v.findViewById(R.id.highScore_text_7),
            v.findViewById(R.id.highScore_text_8),
            v.findViewById(R.id.highScore_text_9),
            v.findViewById(R.id.highScore_text_10)
        )
    }
}