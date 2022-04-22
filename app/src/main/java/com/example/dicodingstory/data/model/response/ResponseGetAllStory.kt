package com.example.dicodingstory.data.model.response

import android.os.Parcelable
import com.example.dicodingstory.data.model.data.ListStory
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseGetAllStory(
    var error:Boolean?,
    var message: String?,
    var listStory: MutableList<ListStory>?
):Parcelable
