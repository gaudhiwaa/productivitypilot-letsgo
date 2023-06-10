package com.bangkit.productivitypilot
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private val images = listOf(R.mipmap.jumbotron1_foreground, R.mipmap.jumbotron2_foreground) // Replace with your image resources

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        viewPager = view.findViewById(R.id.viewPager)
//        tabLayout = view.findViewById(R.id.tabLayout)

        // Set up the adapter for the ViewPager
        val adapter = ImageSliderAdapter(images) // Create your custom adapter to populate the images
        viewPager.adapter = adapter

        // Set up the TabLayout with the ViewPager
//        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//            // Customize the tab if needed (e.g., set text, icons)
//        }.attach()

        // Start auto-scrolling
        startAutoScroll()

        return view
    }

    private fun startAutoScroll() {
        val numPages = images.size
        var currentPage = 0

        val handler = android.os.Handler()
        val runnable = object : Runnable {
            override fun run() {
                currentPage = (currentPage + 1) % numPages
                viewPager.setCurrentItem(currentPage, true)
                handler.postDelayed(this, 4000) // Change the delay as per your requirement (e.g., 3000ms = 3 seconds)
            }
        }

        handler.postDelayed(runnable, 4000) // Start auto-scrolling after an initial delay (e.g., 3000ms = 3 seconds)
    }
}

