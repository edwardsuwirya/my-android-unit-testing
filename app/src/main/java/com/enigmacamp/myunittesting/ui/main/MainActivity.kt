package com.enigmacamp.myunittesting.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.enigmacamp.myunittesting.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val titles = arrayListOf("Sign Up", "Find")
        binding.apply {
            viewPager2.adapter = ViewPagerFragmentAdapter(titles, this@MainActivity)
            getSupportActionBar()?.elevation = 0f
            TabLayoutMediator(
                tabLayout, viewPager2
            ) { tab, position -> tab.setText(titles.get(position)) }.attach()
        }


    }


}