package com.example.dicodingstory.presentation.intent


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dicodingstory.data.UserPreference
import com.example.dicodingstory.databinding.ActivityDicodingStoryBinding
import com.example.dicodingstory.presentation.adabter.LoadingStateAdapter
import com.example.dicodingstory.presentation.adabter.StoryAdapter
import com.example.dicodingstory.presentation.factory.ViewModelFactory
import com.example.dicodingstory.presentation.viewmodel.StoryViewModel

@ExperimentalPagingApi
class DicodingStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDicodingStoryBinding
    private lateinit var adapter: StoryAdapter
    private lateinit var storyViewModel: StoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDicodingStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val userPreference = UserPreference(this)
        val userlogin = userPreference.getUser()

        if(userlogin.token == null){
            val intentToLogin = Intent(this@DicodingStoryActivity,LoginSigupActivity::class.java)
            startActivity(intentToLogin)
            finish()
        }else{
            storyViewModel = ViewModelProvider(this,
                ViewModelFactory(this))[StoryViewModel::class.java]


            binding.rvListStory.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
            adapter = StoryAdapter()

            binding.rvListStory.adapter =  adapter.withLoadStateFooter(footer = LoadingStateAdapter {
                adapter.retry()
            })

            storyViewModel.pagingStory(userlogin.token.toString()).observe(this,{
                showLoading(true)

                Handler(Looper.getMainLooper()).postDelayed({
                    adapter.submitData(lifecycle,it)
                    showLoading(false)


                },500)


            })
        }





        binding.ivAddStory.setOnClickListener {
            val intentToAddStory = Intent(this@DicodingStoryActivity,AddStoryActivity::class.java)
            startActivity(intentToAddStory)

        }

        binding.ivSetting.setOnClickListener{
            val intentToSetting = Intent(this@DicodingStoryActivity,SettingActivity::class.java)
            startActivity(intentToSetting)
        }

        binding.fab.setOnClickListener {
            val intentToMap = Intent(this@DicodingStoryActivity,MapsActivity::class.java)
            startActivity(intentToMap)

        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    companion object {
        const val TAG_DETAIL_STORY = "Tag_data_detail_story"
    }
}