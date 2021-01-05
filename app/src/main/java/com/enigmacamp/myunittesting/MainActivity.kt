package com.enigmacamp.myunittesting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.enigmacamp.myunittesting.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            val userName = userNameTextView.text
            val email = emailTextView.text
            val password = passwordTextView.text
            val confirmedPassword = confirmedPasswordTextView.text
            signUpButton.setOnClickListener {
                val result = RegistrationUtil.validateRegistrationInput(
                    userName.toString(),
                    password.toString(),
                    confirmedPassword.toString(),
                    email.toString()
                )
                if (result) {
                    Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_LONG).show()
                }
            }
        }

    }
}