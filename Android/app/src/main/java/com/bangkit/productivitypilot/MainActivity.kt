package com.bangkit.productivitypilot

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var fullNameEditText: EditText
    private lateinit var usernameEditText: EditText // Added usernameEditText
    private lateinit var signUpButton: Button
    private lateinit var signInTextView: TextView

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    private var usernameTextWatcher: TextWatcher? = null // Track the TextWatcher to remove and reattach

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        fullNameEditText = findViewById(R.id.fullNameEditText)
        usernameEditText = findViewById(R.id.usernameEditText) // Initialize usernameEditText
        signUpButton = findViewById(R.id.signUpButton)
        signInTextView = findViewById(R.id.signInTextView)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Initialize the TextWatcher for usernameEditText
        usernameTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do nothing
            }

            override fun afterTextChanged(s: Editable?) {
                // Convert the input to lowercase
                val lowercaseText = s?.toString()?.toLowerCase()

                // Temporarily remove the TextWatcher to prevent infinite loop
                usernameEditText.removeTextChangedListener(this)

                // Update the EditText with the lowercase text
                usernameEditText.setText(lowercaseText)

                // Move the cursor to the end of the text
                usernameEditText.setSelection(lowercaseText?.length ?: 0)

                // Reattach the TextWatcher
                usernameEditText.addTextChangedListener(this)
            }
        }

        // Attach the TextWatcher to usernameEditText
        usernameEditText.addTextChangedListener(usernameTextWatcher)

        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val fullName = fullNameEditText.text.toString()
            val username = usernameEditText.text.toString() // Get the username input

            if (isValidEmail(email) && isValidPassword(password) && fullName.isNotEmpty() && username.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            val userId = user?.uid

                            if (userId != null) {
                                val userMap = hashMapOf(
                                    "email" to email,
                                    "name" to fullName,
                                    "username" to username // Add username to the userMap
                                )
                                firestore.collection("users")
                                    .document(userId)
                                    .set(userMap)
                                    .addOnSuccessListener {
                                        Toast.makeText(this, "Sign-up successful", Toast.LENGTH_SHORT).show()
                                        val intent = Intent(this, SignUpDetails::class.java)
                                        intent.putExtra("userId", userId)
                                        startActivity(intent)
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(this, "Failed to sign up: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        } else {
                            Toast.makeText(this, "Sign-up failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Invalid email, password, full name, or username", Toast.LENGTH_SHORT).show()
            }
        }

        signInTextView.setOnClickListener {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }
}
