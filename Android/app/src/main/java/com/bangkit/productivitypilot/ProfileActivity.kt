package com.bangkit.productivitypilot
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

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
    }
}
