package com.bangkit.productivitypilot

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import android.widget.Toast

class SignUpDetails : AppCompatActivity() {
    private lateinit var occupationEditText: EditText
    private lateinit var universityEditText: EditText
    private lateinit var locationEditText: EditText
    private lateinit var hobbyEditText: EditText
    private lateinit var registerButton: Button

    private lateinit var firestore: FirebaseFirestore
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_details)

        occupationEditText = findViewById(R.id.occupationEditText)
        universityEditText = findViewById(R.id.universityEditText)
        locationEditText = findViewById(R.id.locationEditText)
        hobbyEditText = findViewById(R.id.hobbyEditText)
        registerButton = findViewById(R.id.registerButton)

        firestore = FirebaseFirestore.getInstance()

        // Action bar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)

        // Get userId from intent
        userId = intent.getStringExtra("userId") ?: ""

        registerButton.setOnClickListener {
            val occupation = occupationEditText.text.toString()
            val institution = universityEditText.text.toString()
            val location = locationEditText.text.toString()
            val hobby = hobbyEditText.text.toString()

            if (occupation.isNotEmpty() && institution.isNotEmpty() && location.isNotEmpty() && hobby.isNotEmpty()) {
                // Update user data in Firestore
                val userMap: Map<String, Any> = hashMapOf(
                    "occupation" to occupation,
                    "institution" to institution,
                    "location" to location,
                    "hobby" to hobby,
                    "userId" to userId,
                    "monthlyProductiveHour" to 0,
                    "userPoint" to 0
                )

                firestore.collection("users")
                    .document(userId)
                    .update(userMap)
                    .addOnSuccessListener {
                        // Add your desired next steps after successful registration
                        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                        // For example, navigate to the profile screen
                        val intent = Intent(this, ProfileActivity::class.java)
                        intent.putExtra("userId", userId) // Pass the userId to the ProfileActivity
                        startActivity(intent)
                        finish() // Optional: Close the current activity to prevent going back
                    }
                    .addOnFailureListener { e ->
                        // Handle failure
                        Toast.makeText(this, "Failed to register: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
