package com.bangkit.productivitypilot
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
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
    private lateinit var vfollower: LinearLayout

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        progressBar = findViewById(R.id.progressBar)
        usernameTextView = findViewById(R.id.usernameTextView)
        nameTextView = findViewById(R.id.nameTextView)
        progressMedalBar = findViewById(R.id.progressMedalBar)
        vfollower = findViewById(R.id.v_follower)


        // Perpindah ke FollowerFollowing Activity
        vfollower.setOnClickListener {
            startActivity(Intent(this, FollowerActivity::class.java))
        }

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
                        val email = document.getString("email")
                        val occupation = document.getString("occupation")
                        val institution = document.getString("institution")
                        val location = document.getString("location")
                        val hobby = document.getString("hobby")
                        val monthlyProductiveHour = document.getLong("monthlyProductiveHour")
                        val userPoint = document.getLong("userPoint")

                        usernameTextView.text = "@$username"
                        nameTextView.text = "$name"
//                        emailTextView.text = "Email: $email"
//                        occupationTextView.text = "Occupation: $occupation"
//                        institutionTextView.text = "Institution: $institution"
//                        locationTextView.text = "Location: $location"
//                        hobbyTextView.text = "Hobby: $hobby"
//                        monthlyProductiveHourTextView.text = "Monthly Productive Hours: $monthlyProductiveHour"
//                        userPointTextView.text = "User Points: $userPoint"
//                        userIdTextView.text = "User ID: $userId"

                        progressBar.visibility = View.GONE // Hide the progress bar
                    }
                }
                .addOnFailureListener { e ->
                    // Handle failure
                    progressBar.visibility = View.GONE // Hide the progress bar
                }


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
                    true

                }
                R.id.menu_statistics -> {
                    // Handle statistics item selection
                    val intent = Intent(this, StatisticActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }





}


