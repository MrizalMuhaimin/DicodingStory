package com.example.dicodingstory.data.model.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ResponseAddNewStory(
    var error: Boolean?,
    var message: String,

):Parcelable
