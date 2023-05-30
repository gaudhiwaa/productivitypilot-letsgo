package com.bangkit.productivitypilot

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var fullNameEditText: EditText
    private lateinit var signUpButton: Button
    private lateinit var signInTextView: TextView // Added signInTextView

    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        fullNameEditText = findViewById(R.id.fullNameEditText)
        signUpButton = findViewById(R.id.signUpButton)
        signInTextView = findViewById(R.id.signInTextView) // Initialize signInTextView

        // Initialize Firebase
        FirebaseApp.initializeApp(this)
        firestore = FirebaseFirestore.getInstance()

        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val fullName = fullNameEditText.text.toString()

            if (isValidEmail(email) && isValidPassword(password) && fullName.isNotEmpty()) {
                // Save user data to Firestore
                val userMap = hashMapOf(
                    "email" to email,
                    "name" to fullName
                )
                firestore.collection("users")
                    .add(userMap)
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(this, "Sign-up successful", Toast.LENGTH_SHORT).show()
                        // Add your desired next steps after successful sign-up
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Failed to sign up: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Invalid email, password, or full name", Toast.LENGTH_SHORT).show()
            }
        }

        // Set click listener for signInTextView
        signInTextView.setOnClickListener {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }

    }

    private fun isValidEmail(email: String): Boolean {
        // Perform email validation logic here
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        // Perform password validation logic here
        return password.length >= 6
    }
}
