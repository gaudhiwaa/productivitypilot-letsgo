package com.bangkit.productivitypilot

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bangkit.productivitypilot.model.StatisticData
import com.google.android.material.bottomnavigation.BottomNavigationView

class StatisticActivity : AppCompatActivity() {

    private lateinit var statisticData: StatisticData
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistic)

        // Initialize the StatisticData object
        statisticData = StatisticData(
            hourMon = "0.8 Hrs",
            hourTue = "0.6 Hrs",
            hourWed = "0.7 Hrs",
            hourThu = "0.5 Hrs",
            hourFri = "0.9 Hrs"
        )

        // Initialize TextViews and display the values from statisticData
        val hourMon = findViewById<TextView>(R.id.hour_mon)
        val hourTue = findViewById<TextView>(R.id.hour_tue)
        val hourWed = findViewById<TextView>(R.id.hour_wed)
        val hourThu = findViewById<TextView>(R.id.hour_thu)
        val hourFri = findViewById<TextView>(R.id.hour_fri)



        hourMon.text = statisticData.hourMon
        hourTue.text = statisticData.hourTue
        hourWed.text = statisticData.hourWed
        hourThu.text = statisticData.hourThu
        hourFri.text = statisticData.hourFri

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                   // Handle home item selection
                    true
                }
                R.id.menu_search -> {
                    // Handle search item selection
                    true
                }
                R.id.menu_profile -> {
                    // Handle profile item selection
                    val intent = Intent(this, ProfileFragment::class.java)
                    startActivity(intent)
                    true
               }
               R.id.menu_studycamera -> {
                    // Handle study camera item selection
                    true

                }
                R.id.menu_statistics -> {
                    //Handle statistics item selection
                    val intent = Intent(this, StatisticActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }


}

