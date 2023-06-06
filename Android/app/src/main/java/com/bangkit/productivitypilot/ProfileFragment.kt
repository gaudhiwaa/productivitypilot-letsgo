package com.bangkit.productivitypilot

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileFragment : Fragment() {
    private lateinit var progressBar: ProgressBar
    private lateinit var usernameTextView: TextView
    private lateinit var nameTextView: TextView
    private lateinit var progressMedalBar: ProgressBar

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        progressBar = view.findViewById(R.id.progressBar)
        usernameTextView = view.findViewById(R.id.usernameTextView)
        nameTextView = view.findViewById(R.id.nameTextView)
        progressMedalBar = view.findViewById(R.id.progressMedalBar)

        // Set the progress to 30%
        progressMedalBar.progress = 30

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity() as AppCompatActivity
        activity.supportActionBar?.hide()

        val userId = arguments?.getString("userId") ?: ""

        if (userId.isNotEmpty()) {
            progressBar.visibility = View.VISIBLE // Show the progress bar

            firestore.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val username = document.getString("username")
                        val name = document.getString("name")

                        usernameTextView.text = "@$username"
                        nameTextView.text = "$name"

                        progressBar.visibility = View.GONE // Hide the progress bar
                    }
                }
                .addOnFailureListener { e ->
                    // Handle failure
                    progressBar.visibility = View.GONE // Hide the progress bar
                }
        }

        bottomNavigationView = view.findViewById(R.id.bottomNavigationView)
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
                    true
                }
                R.id.menu_studycamera -> {
                    // Handle study camera item selection
                    true
                }
                R.id.menu_statistics -> {
                    // Handle statistics item selection
                    val fragment = StatisticFragment()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit()
                    true
                }
                else -> false
            }
        }

        return view
    }
}
