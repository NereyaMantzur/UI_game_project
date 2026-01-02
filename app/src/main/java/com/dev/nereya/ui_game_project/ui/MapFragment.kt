package com.dev.nereya.ui_game_project.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev.nereya.ui_game_project.R
import com.dev.nereya.ui_game_project.model.LeaderBoardList
import com.dev.nereya.ui_game_project.utils.Constants
import com.dev.nereya.ui_game_project.utils.SharedPreferencesManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson


class MapFragment : Fragment(), OnMapReadyCallback {

    private var googleMap: GoogleMap? = null
    private lateinit var leaderBoardList: LeaderBoardList


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v: View = inflater.inflate(R.layout.fragment_map, container, false)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_container) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        mapFragment?.getMapAsync(this)
        return v
    }

    private fun putLocations() {
        val gson = Gson()

        val leaderBoardFromSP: String = SharedPreferencesManager
            .getInstance()
            .getString(Constants.SPKeys.LEADERBOARD_KEY, "")

        leaderBoardList = if (leaderBoardFromSP.isEmpty()) {
            LeaderBoardList()
        } else {
            gson.fromJson(leaderBoardFromSP, LeaderBoardList::class.java) ?: LeaderBoardList()
        }
        for (i in 0..leaderBoardList.leaderBoard.size - 1){
            val lat = leaderBoardList.leaderBoard[i].lat
            val lon = leaderBoardList.leaderBoard[i].lon
            val location = LatLng(lat, lon)
            googleMap?.addMarker(MarkerOptions().position(location).title("Score Location"))
        }
    }

    fun zoom(lat: Double, lon: Double) {
        val location = LatLng(lat, lon)
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
    }

    override fun onMapReady(m: GoogleMap) {
        this.googleMap = m
        val defaultLoc = LatLng(31.50, 35.0)
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLoc, 6.5f))
        putLocations();
    }
}