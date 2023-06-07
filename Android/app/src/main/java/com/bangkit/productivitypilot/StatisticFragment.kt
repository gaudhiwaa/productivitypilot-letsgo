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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_statistic, container, false)

        // Initialize the StatisticData object
        statisticData = StatisticData(
            hourMon = "0.8 Hrs",
            hourTue = "0.6 Hrs",
            hourWed = "0.7 Hrs",
            hourThu = "0.5 Hrs",
            hourFri = "0.9 Hrs"
        )

        // Initialize TextViews and display the values from statisticData
        val hourMon = view.findViewById<TextView>(R.id.hour_mon)
        val hourTue = view.findViewById<TextView>(R.id.hour_tue)
        val hourWed = view.findViewById<TextView>(R.id.hour_wed)
        val hourThu = view.findViewById<TextView>(R.id.hour_thu)
        val hourFri = view.findViewById<TextView>(R.id.hour_fri)

        hourMon.text = statisticData.hourMon
        hourTue.text = statisticData.hourTue
        hourWed.text = statisticData.hourWed
        hourThu.text = statisticData.hourThu
        hourFri.text = statisticData.hourFri


        return view
    }
}


