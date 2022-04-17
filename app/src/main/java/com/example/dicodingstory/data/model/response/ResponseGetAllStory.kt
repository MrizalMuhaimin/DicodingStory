package com.example.dicodingstory.data.model.response

import com.example.dicodingstory.data.model.data.ListStory

data class ResponseGetAllStory(
    var error:Boolean,
    var message: String,
    var listStory: ListStory
)
