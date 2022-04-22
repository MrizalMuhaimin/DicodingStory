package com.example.dicodingstory.presentation.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingstory.data.model.data.ListStory
import com.example.dicodingstory.data.model.response.ResponseGetAllStory
import com.example.dicodingstory.data.network.api.dicodingstory.ApiConfigDicodingStory
import com.google.gson.JsonParser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetAllStoryViewModel:ViewModel() {
    private val _response = MutableLiveData<ResponseGetAllStory>()
    val responseData: LiveData<ResponseGetAllStory> = _response

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun getAllStory( tokenBearer:String){
        _isLoading.value = true;

        val client = ApiConfigDicodingStory.getApiService().getAllStory(token = "Bearer ${tokenBearer}" )
        client.enqueue(object : Callback<ResponseGetAllStory> {

            override fun onResponse(
                call: Call<ResponseGetAllStory>,
                response: Response<ResponseGetAllStory>
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
                    val respon = ResponseGetAllStory(true,jsonObject.get("message").toString(),
                        null
                    )

                    _response.value = respon

                    Log.e(ContentValues.TAG, "onFailure User 2: ${response.message()}")
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<ResponseGetAllStory>, t: Throwable) {
                _isLoading.value = false

                Log.e(ContentValues.TAG, "onFailure get Story: ${t.message.toString()}")
            }
        })


    }

}