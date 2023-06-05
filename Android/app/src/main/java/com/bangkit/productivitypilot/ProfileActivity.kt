package com.bangkit.productivitypilot
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    private lateinit var usernameTextView: TextView
    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var occupationTextView: TextView
    private lateinit var institutionTextView: TextView
    private lateinit var locationTextView: TextView
    private lateinit var hobbyTextView: TextView
    private lateinit var monthlyProductiveHourTextView: TextView
    private lateinit var userPointTextView: TextView
    private lateinit var userIdTextView: TextView
    private lateinit var progressMedalBar: ProgressBar

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        progressBar = findViewById(R.id.progressBar)
        usernameTextView = findViewById(R.id.usernameTextView)
        nameTextView = findViewById(R.id.nameTextView)
        progressMedalBar = findViewById(R.id.progressMedalBar)

        // Set the progress to 30%
        progressMedalBar.progress = 30

//        emailTextView = findViewById(R.id.emailTextView)
//        occupationTextView = findViewById(R.id.occupationTextView)
//        institutionTextView = findViewById(R.id.institutionTextView)
//        locationTextView = findViewById(R.id.locationTextView)
//        hobbyTextView = findViewById(R.id.hobbyTextView)
//        monthlyProductiveHourTextView = findViewById(R.id.monthlyProductiveHourTextView)
//        userPointTextView = findViewById(R.id.userPointTextView)
//        userIdTextView = findViewById(R.id.userIdTextView)

        sharedPrefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val userId = intent.getStringExtra("userId") ?: ""

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
                    true
                }
                R.id.menu_studycamera -> {
                    // Handle study camera item selection
                    val intent = Intent(this, ProductiveCameraActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu_statistics -> {
                    // Handle statistics item selection
                    true
                }
                else -> false
            }

        }

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
