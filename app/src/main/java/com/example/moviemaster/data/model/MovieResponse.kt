package com.example.moviemaster.data.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(

    val page: Int,
    @SerializedName("total_results")
    val totalResults: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    val results: ArrayList<Movie>
)
