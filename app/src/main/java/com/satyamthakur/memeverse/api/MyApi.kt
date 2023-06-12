package com.satyamthakur.memeverse.api

import com.satyamthakur.memeverse.models.MemesResponse
import retrofit2.Response
import retrofit2.http.GET

interface MyApi {

    @GET("gimme/dankmemes/50")
    suspend fun getMemes(): Response<MemesResponse>
}