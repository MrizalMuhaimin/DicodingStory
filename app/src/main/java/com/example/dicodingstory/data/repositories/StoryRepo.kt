package com.example.dicodingstory.data.repositories

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.dicodingstory.data.db.entities.ListStoryEntity
import com.example.dicodingstory.data.db.remote.RemoteMediatorStory
import com.example.dicodingstory.data.db.room.StoryRoomDatabase
import com.example.dicodingstory.data.network.api.dicodingstory.ApiServiceDicodingStory

class StoryRepo (private val storyDatabase: StoryRoomDatabase?, private val apiService: ApiServiceDicodingStory) {

    @ExperimentalPagingApi
    fun find(token: String): LiveData<PagingData<ListStoryEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 8),
            remoteMediator = storyDatabase?.let { RemoteMediatorStory(it, apiService, token) },
            pagingSourceFactory = {
                storyDatabase?.StoryDao()!!.getAllStory()
            }
        ).liveData
    }


    suspend fun getData(): List<ListStoryEntity> {
        return storyDatabase?.StoryDao()!!.findAll()
    }
}