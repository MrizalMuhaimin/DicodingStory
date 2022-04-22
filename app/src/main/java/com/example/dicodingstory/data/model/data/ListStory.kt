package com.example.dicodingstory.data.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListStory(
    var id: String? ,
    var name: String?,
    var description: String?,
    var photoUrl: String?,
    var createdAt: String?,
    var lat: Float?,
    var lon: Float?,
):Parcelable
