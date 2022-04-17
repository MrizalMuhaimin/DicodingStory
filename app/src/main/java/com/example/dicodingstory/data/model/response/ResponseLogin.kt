package com.example.dicodingstory.data.model.response

import android.os.Parcelable
import com.example.dicodingstory.data.model.data.LoginResult
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseLogin(
    var error: Boolean,
    var message: String,
    var loginResult: LoginResult

):Parcelable
