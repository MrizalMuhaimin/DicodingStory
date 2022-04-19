package com.example.dicodingstory.data.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseRegister (
    @field:SerializedName("error")
    var error: Boolean?,
    @field:SerializedName("message")
    var message: String?
    ):Parcelable