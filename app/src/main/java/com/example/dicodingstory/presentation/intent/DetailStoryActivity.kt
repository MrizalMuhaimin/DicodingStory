package com.example.dicodingstory.presentation.intent

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.dicodingstory.R
import com.example.dicodingstory.data.model.data.ListStory
import com.example.dicodingstory.databinding.ActivityDetailStoryBinding

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupData()


    }

    @SuppressLint("SetTextI18n")
    private fun setupData() {
        val data = intent.getParcelableExtra<ListStory>(DicodingStoryActivity.TAG_DETAIL_STORY) as ListStory
        Glide.with(applicationContext)
            .load(data.photoUrl)
            .centerCrop()
            .into(binding.ivStory)

        binding.tvCreateAt.text = "Create At: ${data.createdAt}"
        binding.tvUsername.text = data.name
        binding.tvDeskripsi.text = data.description ?: "-"
    }


}