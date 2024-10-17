package com.example.sydhnymarathonapp
import androidx.appcompat.app.AppCompatActivity

object PointsManager {
    var points: Int = 0
        private set

    fun setPoints(value: Int) {
        points = value
    }

    fun subPoints(value: Int) {
        points = if (points - value >= 0) points - value else 0
    }

    fun resPoints() {
        points = 0
    }

    fun updateActionBarPoints(activity: AppCompatActivity) {
        activity.supportActionBar?.title = "Points: $points"
    }
}