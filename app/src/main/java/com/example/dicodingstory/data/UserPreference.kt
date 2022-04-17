package com.example.dicodingstory.data

import android.content.Context
import com.example.dicodingstory.data.model.data.LoginResult

internal class UserPreference(context: Context){
    companion object{
        private const val PREFS_NAME = "user_pref"
        private const val USERID = "userid"
        private const val NAME = "name"
        private const val TOKEN = "token"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    fun setUser(value: LoginResult) {
        val editor = preferences.edit()
        editor.putString(USERID, value.userid)
        editor.putString(NAME, value.name)
        editor.putString(TOKEN, value.token)
        editor.apply()
    }

    fun getUser(): LoginResult {
        val model = LoginResult()
        model.userid = preferences.getString(USERID,"")
        model.name = preferences.getString(NAME, "")
        model.token = preferences.getString(TOKEN, "")
        return model
    }



}