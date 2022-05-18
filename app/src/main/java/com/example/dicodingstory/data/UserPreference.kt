package com.example.dicodingstory.data

import android.content.Context
import com.example.dicodingstory.data.model.data.LoginResult

class UserPreference(context: Context){
    companion object{
        private const val PREFS_NAME = "user_pref"
        private const val USERID = "userid"
        private const val NAME = "name"
        private const val TOKEN = "token"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    fun setUser(value: LoginResult?) {
        val editor = preferences.edit()
        editor.putString(USERID, value?.userId)
        editor.putString(NAME, value?.name)
        editor.putString(TOKEN, value?.token)
        editor.apply()
    }

    fun getUser(): LoginResult {
        val model = LoginResult()
        model.userId = preferences.getString(USERID,null)
        model.name = preferences.getString(NAME, null)
        model.token = preferences.getString(TOKEN, null)
        return model
    }



}