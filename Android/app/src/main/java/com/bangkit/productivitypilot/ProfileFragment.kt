package com.bangkit.productivitypilot

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment() {
    private lateinit var progressBar: ProgressBar
    private lateinit var usernameTextView: TextView
    private lateinit var nameTextView: TextView
    private lateinit var progressMedalBar: ProgressBar

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


        val username = UserManager.getUsername()
        val name = UserManager.getName()
        updateUI(username, name)



        return view
    }


    private fun updateUI(username: String?, name: String?) {
        usernameTextView.text = "@$username"
        nameTextView.text = "$name"
    }

}
