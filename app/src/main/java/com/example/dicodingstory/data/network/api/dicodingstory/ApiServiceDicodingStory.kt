package com.example.dicodingstory.data.network.api.dicodingstory



import com.example.dicodingstory.data.model.response.*
import com.example.dicodingstory.util.VERSION
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiServiceDicodingStory {

    @POST("/${VERSION}/register")
    fun createAccount(
        @Body requestBody: Map<String, String>
    ): Call<ResponseRegister>


    @POST("/${VERSION}/login")
    fun loginAccount(
        @Body requestBody: Map<String, String>
    ): Call<ResponseLogin>

    @Multipart
    @POST("/${VERSION}/stories")
    fun addNewStory(
        @Header("Authorization") token: String,
        @Part ("description") description: RequestBody?,
        @Part photo: MultipartBody.Part,
        @Part ("lat ") lat: Float?,
        @Part("lon") lon: Float?

    ): Call<ResponseAddNewStory>

    @Multipart
    @POST("/${VERSION}/stories/guest")
    fun addNewStoryGuest(
        @Body requestBody: Map<String, String>
    ): Call<ResponseAddNewStory>

    @GET("/${VERSION}/stories")
    fun getAllStory(
        @Header("Authorization") token: String,

    ):Call<ResponseGetAllStory>

    @GET("/${VERSION}/stories")
    suspend fun getStory(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("location") location: Int? = null

    ): ResponseStory


}