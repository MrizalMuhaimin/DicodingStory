package com.example.dicodingstory.data.network.api.dicodingstory



import com.example.dicodingstory.data.model.response.ResponseAddNewStory
import com.example.dicodingstory.data.model.response.ResponseGetAllStory
import com.example.dicodingstory.data.model.response.ResponseLogin
import com.example.dicodingstory.data.model.response.ResponseRegister
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiServiceDicodingStory {
    @POST("/register")
    fun createAccount(
        @Body requestBody: Map<String, String>
    ): Call<ResponseRegister>

    @POST("/login")
    fun loginAccount(
        @Body requestBody: Map<String, String>
    ): Call<ResponseLogin>

    @POST("/stories")
    fun addNewStory(
        @Body requestBody: RequestBody
    ): Call<ResponseAddNewStory>

    @POST("/stories/guest")
    fun addNewStoryGuest(
        @Body requestBody: RequestBody
    ): Call<ResponseAddNewStory>

    @GET("/stories")
    fun getAllStory(

    ):Call<ResponseGetAllStory>


}