package com.bangkit.productivitypilot

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.firestore.FirebaseFirestore

class SearchFragment : Fragment() {
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        val appCompatActivity = requireActivity() as AppCompatActivity
        appCompatActivity.setSupportActionBar(toolbar)

        firestore = FirebaseFirestore.getInstance()

        toolbar.setNavigationOnClickListener {
            // Handle the back button click
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, HomeFragment())
            transaction.commit()
        }

        val searchEditText: EditText = view.findViewById(R.id.searchEditText)
//        val searchResultsContainer: LinearLayout = view.findViewById(R.id.searchResultsContainer)

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val searchTerm = s.toString()
                searchUsernames(searchTerm)
            }
        })

        return view
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