package com.bangkit.productivitypilot

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class SignIn : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signInButton: Button
    private lateinit var signUpTextView: TextView // Added signInTextView

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        signInButton = findViewById(R.id.signInButton)
        signUpTextView = findViewById(R.id.signUpTextView) // Initialize signInTextView

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        signInButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (isValidEmail(email) && isValidPassword(password)) {
                signInUser(email, password)
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }

        signUpTextView.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signInUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign-in success, navigate to the desired activity
                    Toast.makeText(this, "Sign-in successful", Toast.LENGTH_SHORT).show()
                    // Add your desired next steps after successful sign-in
                } else {
                    // Sign-in failed
                    val exception = task.exception
                    if (exception is FirebaseAuthInvalidUserException) {
                        Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show()
                    } else if (exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Sign-in failed: ${exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
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
