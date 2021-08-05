package com.example.moviemaster.data.model

import com.google.gson.annotations.SerializedName

data class Movie(

    val id: Int,
    val popularity: Double,
    @SerializedName("vote_count")
    val voteCount: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    val title: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val video: Boolean,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("overview")
    val overView: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("runtime")
    val runTime: String,
    @SerializedName("genres")
    val genres: List<Genre>,
)
