package com.example.dicodingstory.presentation.intent

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.paging.ExperimentalPagingApi
import com.bumptech.glide.Glide
import com.example.dicodingstory.R
import com.example.dicodingstory.data.db.entities.ListStoryEntity
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    @OptIn(ExperimentalPagingApi::class)
    @SuppressLint("SetTextI18n")
    private fun setupData() {
        val data = intent.getParcelableExtra<ListStory>(DicodingStoryActivity.TAG_DETAIL_STORY) as ListStoryEntity
        Glide.with(applicationContext)
            .load(data.photoUrl)
            .centerCrop()
            .into(binding.ivStory)

        val textCreate = resources.getString(R.string.createAt)
        binding.tvCreateAt.text = "${textCreate} ${data.createdAt}"
        binding.tvUsername.text = data.name
        binding.tvDeskripsi.text = data.description ?: "-"
    }


}