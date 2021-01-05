package com.enigmacamp.myunittesting.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.enigmacamp.goldmarket.utils.ResourceStatus
import com.enigmacamp.myunittesting.data.model.UserRegistration
import com.enigmacamp.myunittesting.utils.RegistrationUtil
import com.enigmacamp.myunittesting.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel

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
                viewModel.userRegistration(
                    UserRegistration(
                        userName.toString(),
                        password.toString(),
                        confirmedPassword.toString(),
                        email.toString()
                    )
                )
            }
        }
        initViewModel()
        subscribe()
    }

    fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    fun subscribe() {
        viewModel.registrationStatusLiveData.observe(this, {
            when (it.status) {
                ResourceStatus.LOADING -> Log.d("Main", "Loading")
                ResourceStatus.SUCCESS -> Toast.makeText(
                    this@MainActivity,
                    "Success",
                    Toast.LENGTH_LONG
                ).show()
                ResourceStatus.ERROR -> Toast.makeText(
                    this@MainActivity,
                    "Error",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}