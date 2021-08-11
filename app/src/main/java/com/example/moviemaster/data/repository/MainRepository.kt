package com.example.moviemaster.data.repository

import com.example.moviemaster.data.model.GenreResponse
import com.example.moviemaster.data.remote.MovieService
import io.reactivex.Observable
import javax.inject.Inject

class MainRepository@Inject constructor(private val movieService: MovieService) {

    fun getGenres(): Observable<GenreResponse> {
        return movieService.getGenres()
    }
}