package com.example.dicodingstory.presentation.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingstory.data.model.response.ResponseRegister
import com.example.dicodingstory.data.network.api.dicodingstory.ApiConfigDicodingStory
import com.google.gson.JsonParser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateAccountViewModel:ViewModel() {
        private val _response = MutableLiveData<ResponseRegister>()
        val responseData: LiveData<ResponseRegister> = _response

        private val _isLoading = MutableLiveData<Boolean>()
        val isLoading: LiveData<Boolean> = _isLoading

        fun register(name:String, email:String, password:String){
            _isLoading.value = true
            val values = mapOf("name" to name , "email" to email, "password" to password)
            val client = ApiConfigDicodingStory.getApiService().createAccount(values)
            client.enqueue(object : Callback<ResponseRegister> {

                override fun onResponse(
                    call: Call<ResponseRegister>,
                    response: Response<ResponseRegister>
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
                        val respon = ResponseRegister(true,jsonObject.get("message").toString())


                        _response.value = respon
                        Log.e(ContentValues.TAG, "onFailure User 2: ${jsonObject.get("message")}")
                    }
                    _isLoading.value = false
                }

                override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
                    _isLoading.value = false
                    _response.value = ResponseRegister(true,t.message.toString())
                    Log.e(ContentValues.TAG, "onFailure User: ${t.message.toString()}")
                }
            })
        }

}