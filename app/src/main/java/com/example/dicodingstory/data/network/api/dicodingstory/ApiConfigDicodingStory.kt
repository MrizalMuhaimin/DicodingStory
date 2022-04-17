package com.example.dicodingstory.data.network.api.dicodingstory

import com.example.dicodingstory.BuildConfig
import com.example.dicodingstory.util.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiConfigDicodingStory {
    companion object{
        private val logger = if(BuildConfig.DEBUG) { HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.BODY) }else { HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.NONE) }

        fun getApiService():ApiServiceDicodingStory {
            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiServiceDicodingStory::class.java)
        }


    }
}