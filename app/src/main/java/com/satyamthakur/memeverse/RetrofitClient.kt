package com.satyamthakur.memeverse

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://meme-api.com/"

    private val retrofit: Retrofit by lazy {
        val httpClient = OkHttpClient.Builder().build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }

    val apiService: MyApi by lazy {
        retrofit.create(MyApi::class.java)
    }
}