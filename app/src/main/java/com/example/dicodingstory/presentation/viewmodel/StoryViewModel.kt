package com.example.dicodingstory.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.dicodingstory.data.db.entities.ListStoryEntity
import com.example.dicodingstory.data.repositories.StoryRepo

@ExperimentalPagingApi
class StoryViewModel(private val storyRepo: StoryRepo) : ViewModel() {

    suspend fun getData(): List<ListStoryEntity>{
        return storyRepo.getData()
    }

    val pagingStory: (String) -> LiveData<PagingData<ListStoryEntity>> = { token: String ->
        storyRepo.find("Bearer $token").cachedIn(viewModelScope)
    }
}