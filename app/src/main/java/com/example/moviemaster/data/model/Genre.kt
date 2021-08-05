package com.example.moviemaster.data.model

import com.google.gson.annotations.SerializedName

data class Genre(

    val id: Int,
    @SerializedName("name")
    val genre: String,
    )