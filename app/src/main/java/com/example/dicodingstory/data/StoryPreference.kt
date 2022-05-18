package com.example.dicodingstory.data

import android.content.Context

class StoryPreference(context: Context){
    companion object{
        private const val PREFS_NAME = "story_pref"
        private const val DATA = "data"

    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setStory(value: String?) {
        val editor = preferences.edit()
        editor.putString(DATA, value)
        editor.apply()
    }

    fun getStory():  String {
        val data   = preferences.getString(DATA,null)
        return data.toString()
    }
}