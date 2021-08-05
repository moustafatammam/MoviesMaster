package com.example.moviemaster.data

import com.example.moviemaster.data.model.GenreResponse
import com.example.moviemaster.data.model.MovieResponse
import com.example.moviemaster.data.remote.MovieService
import io.reactivex.Observable

class Repository(private val movieService: MovieService){

    fun getMovies(page: Int): Observable<MovieResponse>{
        return movieService.getMovies(page)
    }

    fun getGenres(): Observable<GenreResponse>{
        return movieService.getGenres()
    }

    fun getSearchedMovies(searchQuery: String, page: Int): Observable<MovieResponse>{
        return movieService.getSearchedMovies(searchQuery, page)
    }
}