package com.example.dicodingstory.di

import android.content.Context
import com.example.dicodingstory.data.db.room.StoryRoomDatabase
import com.example.dicodingstory.data.network.api.dicodingstory.ApiConfigDicodingStory
import com.example.dicodingstory.data.network.api.dicodingstory.ApiServiceDicodingStory
import com.example.dicodingstory.data.repositories.StoryRepo

object Injection {
    fun provideRepo(context: Context?): StoryRepo {
        val database = context?.let { StoryRoomDatabase.getDatabase(it) }
        val apiService = ApiConfigDicodingStory.getApiService()
        return StoryRepo(database, apiService)
    }
}