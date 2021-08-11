package com.example.moviemaster.data.repository

import com.example.moviemaster.data.model.MovieResponse
import com.example.moviemaster.data.remote.MovieService
import io.reactivex.Observable
import javax.inject.Inject

class MovieListRepository @Inject constructor(private val movieService: MovieService) {

    fun getMovies(page: Int, genre: String): Observable<MovieResponse> {
        return movieService.getMovies(page, genre)
    }

    fun getSearchedMovies(searchQuery: String, page: Int): Observable<MovieResponse>{
        return movieService.getSearchedMovies(searchQuery, page)
    }

}