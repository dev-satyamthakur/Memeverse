package com.satyamthakur.memeverse.api

import com.satyamthakur.memeverse.models.MemesResponse
import retrofit2.Response
import retrofit2.http.GET

interface MyApi {

    @GET("gimme/memes/20")
    suspend fun getMemes(): Response<MemesResponse>

    @GET("gimme/dankmemes/20")
    suspend fun getDankMemes(): Response<MemesResponse>
}