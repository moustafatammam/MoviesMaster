package com.example.moviemaster.data.model

import com.google.gson.annotations.SerializedName


data class Cast(
    @SerializedName("cast_id")
    val castId: Int = 0,
    @SerializedName("credit_id")
    val creditId: String? = null,
    val sortingId: Int = 0,
    val character: String? = null,
    val gender: Int = 0,
    val id: Int = 0,
    val name: String? = null,
    val order: Int = 0,
    @SerializedName("profile_path")
    val profilePath: String? = null
)



