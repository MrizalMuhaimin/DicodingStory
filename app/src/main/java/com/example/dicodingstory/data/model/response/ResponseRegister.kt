package com.example.dicodingstory.data.model.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseRegister (
    var error: Boolean,
    var massage: String
    ):Parcelable