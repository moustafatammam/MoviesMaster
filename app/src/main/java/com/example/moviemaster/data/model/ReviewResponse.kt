package com.example.moviemaster.data.model

import com.google.gson.annotations.SerializedName


class ReviewResponse(
    @SerializedName("results")
    val reviews: List<Review?> = ArrayList()
)


