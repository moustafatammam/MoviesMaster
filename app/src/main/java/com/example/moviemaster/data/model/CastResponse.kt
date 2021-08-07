package com.example.moviemaster.data.model

import com.google.gson.annotations.SerializedName


data class CastResponse(
    @SerializedName("cast")
    var cast: List<Cast?> = ArrayList()
)