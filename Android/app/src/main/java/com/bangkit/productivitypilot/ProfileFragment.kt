package com.bangkit.productivitypilot

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment() {
    private lateinit var progressBar: ProgressBar
    private lateinit var usernameTextView: TextView
    private lateinit var nameTextView: TextView
    private lateinit var progressMedalBar: ProgressBar

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        progressBar = view.findViewById(R.id.progressBar)
        usernameTextView = view.findViewById(R.id.usernameTextView)
        nameTextView = view.findViewById(R.id.nameTextView)
        progressMedalBar = view.findViewById(R.id.progressMedalBar)

        // Set the progress to 30%
        progressMedalBar.progress = 30

        sharedPrefs = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

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

                        // Save the user information in SharedPreferences
                        saveUserInformation(username, name)

                        // Update the UI
                        updateUI(username, name)

                        progressBar.visibility = View.GONE // Hide the progress bar
                    }
                }
                .addOnFailureListener { e ->
                    // Handle failure
                    progressBar.visibility = View.GONE // Hide the progress bar
                }
        } else {
            // Retrieve the user information from SharedPreferences
            val username = sharedPrefs.getString("username", "")
            val name = sharedPrefs.getString("name", "")

            // Update the UI
            updateUI(username, name)
        }

        return view
    }

    private fun saveUserInformation(username: String?, name: String?) {
        val editor = sharedPrefs.edit()
        editor.putString("username", username)
        editor.putString("name", name)
        editor.apply()
    }

    private fun updateUI(username: String?, name: String?) {
        usernameTextView.text = "@$username"
        nameTextView.text = "$name"
    }
}
