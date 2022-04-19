package com.example.dicodingstory.data.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginResult(
    var userId:String? = null,
    var name: String? = null,
    var token: String? = null
):Parcelable
