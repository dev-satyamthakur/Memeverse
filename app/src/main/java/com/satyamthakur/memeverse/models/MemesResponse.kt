package com.satyamthakur.memeverse.models

data class MemesResponse(
    val count: Int,
    val memes: List<Meme>
)