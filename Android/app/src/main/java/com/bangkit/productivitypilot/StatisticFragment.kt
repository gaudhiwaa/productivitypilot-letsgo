package com.bangkit.productivitypilot

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bangkit.productivitypilot.model.StatisticData

class StatisticFragment : Fragment() {

    private lateinit var statisticData: StatisticData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Inisialisasi objek StatisticData
        StatisticData(
            hourMon = "0.8 Hrs",
            hourTue = "0.6 Hrs",
            hourWed = "0.7 Hrs",
            hourThu = "0.5 Hrs",
            hourFri = "0.9 Hrs",
            hourSat = "0.4 Hrs",
            hourSun = "0.3 Hrs"
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_statistic, container, false)

        // Menginisialisasi TextView dan menampilkan nilai dari statisticData
        val hourMon = rootView.findViewById<TextView>(R.id.hour_mon)
        val hourTue = rootView.findViewById<TextView>(R.id.hour_tue)
        val hourWed = rootView.findViewById<TextView>(R.id.hour_wed)
        val hourThu = rootView.findViewById<TextView>(R.id.hour_thu)
        val hourFri = rootView.findViewById<TextView>(R.id.hour_fri)
        val hourSat = rootView.findViewById<TextView>(R.id.hour_sat)
        val hourSun = rootView.findViewById<TextView>(R.id.hour_sun)


        hourMon.text = statisticData.hourMon
        hourTue.text = statisticData.hourTue
        hourWed.text = statisticData.hourWed
        hourThu.text = statisticData.hourThu
        hourFri.text = statisticData.hourFri
        hourSat.text = statisticData.hourSat
        hourSun.text = statisticData.hourSun


        return rootView
    }

    companion object {

    }
}
