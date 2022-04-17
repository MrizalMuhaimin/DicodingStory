package com.example.dicodingstory.presentation.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingstory.data.model.response.ResponseLogin
import com.example.dicodingstory.data.network.api.dicodingstory.ApiConfigDicodingStory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginAccountViewModel:ViewModel() {
    private val _response = MutableLiveData<ResponseLogin>()
    val response: LiveData<ResponseLogin> = _response

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(email:String, password:String){
        _isLoading.value = true
        val values = mapOf("email" to email, "password" to password)
        val client = ApiConfigDicodingStory.getApiService().loginAccount(values)
        client.enqueue(object : Callback<ResponseLogin> {
            override fun onResponse(
                call: Call<ResponseLogin>,
                response: Response<ResponseLogin>
            ) {

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if(responseBody != null){
                        _response.value = responseBody!!
                    }
                }else {
                    Log.e(ContentValues.TAG, "onFailure User 2: ${response.message()}")
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure User: ${t.message.toString()}")
            }
        })
    }
}