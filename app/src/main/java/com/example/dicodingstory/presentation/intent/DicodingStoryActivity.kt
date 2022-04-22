package com.example.dicodingstory.presentation.intent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dicodingstory.data.UserPreference
import com.example.dicodingstory.databinding.ActivityDicodingStoryBinding
import com.example.dicodingstory.presentation.adabter.ListStoryAdapter
import com.example.dicodingstory.presentation.viewmodel.GetAllStoryViewModel

class DicodingStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDicodingStoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDicodingStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val userPreference = UserPreference(this)
        val userlogin = userPreference.getUser()

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(GetAllStoryViewModel::class.java)


        mainViewModel.getAllStory(userlogin.token.toString())

        binding.rvListStory.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)

        mainViewModel.responseData.observe(this,{
            val adapter = ListStoryAdapter(it.listStory)
            binding.rvListStory.adapter = adapter
        })




        binding.ivSetting.setOnClickListener{
            val intentToSetting = Intent(this@DicodingStoryActivity,SettingActivity::class.java)
            startActivity(intentToSetting)
            finish()
        }

    }

    companion object {
        const val TAG_DETAIL_STORY = "Tag_data_detail_story"
    }
}