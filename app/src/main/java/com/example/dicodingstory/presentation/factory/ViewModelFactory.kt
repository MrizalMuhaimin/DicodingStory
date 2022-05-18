package com.example.dicodingstory.presentation.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.example.dicodingstory.di.Injection
import com.example.dicodingstory.presentation.viewmodel.StoryViewModel

@ExperimentalPagingApi
class ViewModelFactory(private val context: Context?) : ViewModelProvider.NewInstanceFactory() {


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(StoryViewModel::class.java) -> {
                return StoryViewModel(Injection.provideRepo(context)) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}