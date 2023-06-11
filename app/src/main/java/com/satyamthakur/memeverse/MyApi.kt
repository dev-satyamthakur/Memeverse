package com.satyamthakur.memeverse

import retrofit2.Response
import retrofit2.http.GET

interface MyApi {

    @GET("gimme/memes")
    suspend fun getMemes(): Response<MemeResponse>
}