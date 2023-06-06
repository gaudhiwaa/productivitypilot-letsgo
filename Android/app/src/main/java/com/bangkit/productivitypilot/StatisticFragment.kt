package com.bangkit.productivitypilot
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bangkit.productivitypilot.model.StatisticData
import com.google.android.material.bottomnavigation.BottomNavigationView

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

        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView?.setOnNavigationItemSelectedListener { item ->
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
                    // Replace `YourProfileFragment` with the actual fragment you want to navigate to
                    val fragment = Fragment()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit()
                    true
                }
                R.id.menu_studycamera -> {
                    // Handle study camera item selection
                    true
                }
                R.id.menu_statistics -> {
                    // Handle statistics item selection
                    // No need to navigate since the current fragment is already the statistics fragment
                    true
                }
                else -> false
            }
        }

        return view
    }
}


