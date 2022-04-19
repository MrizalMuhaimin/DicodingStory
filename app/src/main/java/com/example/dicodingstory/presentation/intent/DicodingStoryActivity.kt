package com.example.dicodingstory.presentation.intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dicodingstory.R
import com.example.dicodingstory.data.UserPreference
import com.example.dicodingstory.databinding.ActivityDicodingStoryBinding

class DicodingStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDicodingStoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDicodingStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val userPreference = UserPreference(this)

        val loginResult = userPreference.getUser()

        binding.tvCoba.text = loginResult.userId

    }
}