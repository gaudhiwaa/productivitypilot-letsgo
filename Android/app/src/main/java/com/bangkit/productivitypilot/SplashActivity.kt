package com.bangkit.productivitypilot

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val btSignIn = findViewById<Button>(R.id.bt_signIn)
        val btSignUp = findViewById<Button>(R.id.bt_signUp)


        btSignIn.setOnClickListener {
            startActivity(Intent(this, SignIn::class.java))
        }

        btSignUp.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
