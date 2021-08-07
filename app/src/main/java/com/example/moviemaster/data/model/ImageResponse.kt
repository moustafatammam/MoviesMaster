package com.example.moviemaster.data.model

import com.google.gson.annotations.SerializedName

class ImageResponse(

    val id: Int,
    val backdrops: List<Image>,
    val posters: List<Image>,
)
