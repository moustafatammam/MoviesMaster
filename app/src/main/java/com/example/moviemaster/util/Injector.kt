package com.example.moviemaster.util

import com.example.moviemaster.data.Repository
import com.example.moviemaster.data.remote.ApiClient

class Injector {

    private val movieService = ApiClient.buildMoviesService()

    val repository = Repository(movieService)
}