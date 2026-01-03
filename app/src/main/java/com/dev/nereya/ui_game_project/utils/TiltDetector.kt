package com.dev.nereya.ui_game_project.utils

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.dev.nereya.ui_game_project.interfaces.TiltCallback

class TiltDetector(context: Context, private val axis: Int, private val tiltCallback: TiltCallback) {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private var timestamp: Long = 0L
    private lateinit var sensorEventListener: SensorEventListener

    init {
        initEventListener()
    }

    private fun initEventListener() {
        sensorEventListener = object : SensorEventListener {
            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

            override fun onSensorChanged(event: SensorEvent) {
                val x = event.values[0]
                val y = event.values[1]
                calculateTilt(x, y)
            }
        }
    }

    private fun calculateTilt(x: Float, y: Float) {
        if (System.currentTimeMillis() - timestamp >= 500) {

            if (axis == 0) {
                if (x > 3.0) {
                    timestamp = System.currentTimeMillis()
                    tiltCallback.tiltY() // Left
                }
                else if (x < -3.0) {
                    timestamp = System.currentTimeMillis()
                    tiltCallback.tiltX() // Right
                }
            }

            if (axis == 1) {
                if (y >= 7.0) {
                    timestamp = System.currentTimeMillis()
                    tiltCallback.tiltY() // Slow Down
                }
                else if (y <= 3.0) {
                    timestamp = System.currentTimeMillis()
                    tiltCallback.tiltX() // Speed Up
                }
            }
        }
    }

    fun start() {
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun stop() {
        sensorManager.unregisterListener(sensorEventListener, sensor)
    }
}