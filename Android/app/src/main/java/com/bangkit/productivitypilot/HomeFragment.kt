package com.bangkit.productivitypilot

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var fullNameTextView: TextView
    private lateinit var monthlyProductiveHour: TextView
    private lateinit var monthlyProductiveHour2: TextView
    private lateinit var userPoint: TextView
    private lateinit var dailyProductiveHour: TextView
    private lateinit var weeklyProductiveHour: TextView
    private lateinit var firestore: FirebaseFirestore

    private val images = listOf(R.mipmap.jumbotron1_foreground, R.mipmap.jumbotron2_foreground)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        fullNameTextView = view.findViewById(R.id.fullName)
        monthlyProductiveHour = view.findViewById(R.id.monthlyProductiveHour)
        monthlyProductiveHour2 = view.findViewById(R.id.monthlyProductiveHourx)
        userPoint = view.findViewById(R.id.userPoint)
        dailyProductiveHour = view.findViewById(R.id.dailyProductiveHour)
        weeklyProductiveHour = view.findViewById(R.id.weeklyProductiveHour)
        viewPager = view.findViewById(R.id.viewPager)

        val adapter = ImageSliderAdapter(images)
        viewPager.adapter = adapter
        startAutoScroll()

        firestore = FirebaseFirestore.getInstance()
        val userCollection = firestore.collection("users")

        // Get the current user's ID
        val currentUserID = UserManager.getUserId()

        userCollection.document(currentUserID!!).get().addOnSuccessListener { document ->
            if (document.exists()) {
                val fullName = document.getString("name")
                val monthlyProductiveHourValue = document.getLong("monthlyProductiveHour")
                val userPointValue = document.getLong("userPoint")
                val dailyProductiveHourValue = document.getLong("dailyProductiveHour")
                val weeklyProductiveHourValue = document.getLong("weeklyProductiveHour")
                fullNameTextView.text = fullName
                monthlyProductiveHour.text = monthlyProductiveHourValue?.toString() + " Hour"
                monthlyProductiveHour2.text = monthlyProductiveHourValue?.toString() + " Hour"
                userPoint.text = userPointValue?.toString() + " Point"
                dailyProductiveHour.text = dailyProductiveHourValue?.toString() + " Hour"
                weeklyProductiveHour.text = weeklyProductiveHourValue?.toString() + " Hour"
            }
        }.addOnFailureListener { exception ->
            // Handle the failure case
        }

        val cardContainer: LinearLayout = view.findViewById(R.id.cardContainer)

        userCollection.get().addOnSuccessListener { result ->
            val currentUser = UserManager.getUserId()
            val following = UserManager.getFollowingList()

            for (document in result) {
                val userId = document.getString("userId")
                if (userId != currentUser && !following.contains(userId)) {
                    val occupation = document.getString("occupation") ?: ""
                    val institution = document.getString("institution") ?: ""
                    val name = document.getString("name") ?: ""

                    val cardView = layoutInflater.inflate(R.layout.network_card_layout, cardContainer, false)

                    val nameTextView = cardView.findViewById<TextView>(R.id.nameTextView)
                    val occupationTextView = cardView.findViewById<TextView>(R.id.occupationTextView)
                    val institutionTextView = cardView.findViewById<TextView>(R.id.institutionTextView)
                    val followButton = cardView.findViewById<Button>(R.id.followButton)

                    val firstName = name.split(" ").firstOrNull() ?: ""
                    nameTextView.text = firstName
                    occupationTextView.text = occupation
                    institutionTextView.text = institution

                    followButton.setOnClickListener {
                        // Handle follow button click
                        // Add code to update user's following list in Firebase Firestore
                    }

                    cardContainer.addView(cardView)
                }
            }
        }.addOnFailureListener { exception ->
            // Handle the failure case
        }

        val searchEditText: EditText = view.findViewById(R.id.searchEditText)
//        val searchResultsContainer: LinearLayout = view.findViewById(R.id.searchResultsContainer)

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val searchTerm = s.toString()
//                searchUsernames(searchTerm)
            }
        })


        searchEditText.setOnClickListener {
            // Handle the click event and navigate to fragment_search.xml
            val fragment = SearchFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout,fragment)
            fragmentTransaction.commit()
        }




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
                handler.postDelayed(this, 4000)
            }
        }

        handler.postDelayed(runnable, 4000)
    }

    private fun searchUsernames(searchTerm: String) {
        val userCollection = firestore.collection("users")

        // Clear existing search results
        val searchResultsContainer: LinearLayout = view?.findViewById(R.id.searchResultsContainer) ?: return
        searchResultsContainer.removeAllViews()

        userCollection.whereGreaterThanOrEqualTo("username", searchTerm)
            .whereLessThanOrEqualTo("username", searchTerm + "\uf8ff")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val username = document.getString("username") ?: ""

                    val usernameTextView = TextView(requireContext())
                    usernameTextView.text = username

                    searchResultsContainer.addView(usernameTextView)
                }
            }
            .addOnFailureListener { exception ->
                // Handle the failure case
            }
    }
}
