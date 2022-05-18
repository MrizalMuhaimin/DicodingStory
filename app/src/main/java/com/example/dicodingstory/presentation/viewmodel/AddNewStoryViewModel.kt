package com.example.dicodingstory.presentation.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingstory.data.model.response.ResponseAddNewStory
import com.example.dicodingstory.data.network.api.dicodingstory.ApiConfigDicodingStory
import com.google.gson.JsonParser
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddNewStoryViewModel:ViewModel() {
    private val _response = MutableLiveData<ResponseAddNewStory>()
    val responseData: LiveData<ResponseAddNewStory> = _response

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun addStory(tokenBearer:String, description: RequestBody?, photo: MultipartBody.Part, lat:Float?, lon:Float?){
        _isLoading.value = true
        val client = ApiConfigDicodingStory.getApiService().addNewStory(token = "Bearer ${tokenBearer}",description,photo,lat,lon)
        client.enqueue(object : Callback<ResponseAddNewStory> {

            override fun onResponse(
                call: Call<ResponseAddNewStory>,
                response: Response<ResponseAddNewStory>
            ) {

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if(responseBody != null){
                        _response.value = responseBody!!
                    }

                }else {

                    val responseErrorBody = response.errorBody()?.string()
                    val jsonParser = JsonParser()
                    val jsonObject = jsonParser.parse(responseErrorBody).asJsonObject
                    val respon = ResponseAddNewStory(true,jsonObject.get("message").toString())


                    _response.value = respon
                    Log.e(ContentValues.TAG, "onFailure User 2: ${jsonObject.get("message")}")
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<ResponseAddNewStory>, t: Throwable) {
                _isLoading.value = false
                _response.value = ResponseAddNewStory(true,t.message.toString())
                Log.e(ContentValues.TAG, "onFailure User: ${t.message.toString()}")
            }
        })
    }
}