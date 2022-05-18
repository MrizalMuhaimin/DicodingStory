package com.example.dicodingstory.data.model.response

import android.os.Parcelable
import com.example.dicodingstory.data.db.entities.ListStoryEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseStory(
    val error:Boolean,
    val message: String,
    val listStory: List<ListStoryEntity>
): Parcelable
