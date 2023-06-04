package com.bangkit.productivitypilot

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bangkit.productivitypilot.model.StatisticData

class StatisticActivity : AppCompatActivity() {

    private lateinit var statisticData: StatisticData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistic)

        // Initialize the StatisticData object
        statisticData = StatisticData(
            hourMon = "0.8 Hrs",
            hourTue = "0.6 Hrs",
            hourWed = "0.7 Hrs",
            hourThu = "0.5 Hrs",
            hourFri = "0.9 Hrs",
            hourSat = "0.4 Hrs",
            hourSun = "0.3 Hrs"
        )

        // Initialize TextViews and display the values from statisticData
        val hourMon = findViewById<TextView>(R.id.hour_mon)
        val hourTue = findViewById<TextView>(R.id.hour_tue)
        val hourWed = findViewById<TextView>(R.id.hour_wed)
        val hourThu = findViewById<TextView>(R.id.hour_thu)
        val hourFri = findViewById<TextView>(R.id.hour_fri)
        val hourSat = findViewById<TextView>(R.id.hour_sat)
        val hourSun = findViewById<TextView>(R.id.hour_sun)

        hourMon.text = statisticData.hourMon
        hourTue.text = statisticData.hourTue
        hourWed.text = statisticData.hourWed
        hourThu.text = statisticData.hourThu
        hourFri.text = statisticData.hourFri
        hourSat.text = statisticData.hourSat
        hourSun.text = statisticData.hourSun
    }
}

