package com.example.moviemaster.data

import com.example.moviemaster.data.model.*
import com.example.moviemaster.data.remote.MovieService
import io.reactivex.Observable
import javax.inject.Inject

class Repository @Inject constructor(private val movieService: MovieService){

    fun getMovies(page: Int, genre: String): Observable<MovieResponse>{
        return movieService.getMovies(page, genre)
    }

    fun getGenres(): Observable<GenreResponse>{
        return movieService.getGenres()
    }

    fun getSearchedMovies(searchQuery: String, page: Int): Observable<MovieResponse>{
        return movieService.getSearchedMovies(searchQuery, page)
    }

    fun getMovieImages(movieId: Int?): Observable<ImageResponse>{
        return movieService.getMovieImages(movieId)
    }

    fun getMovieCast(movieId: Int?): Observable<CastResponse>{
        return movieService.getMovieCast(movieId)
    }

    fun getMovieReview(movieId: Int?): Observable<ReviewResponse>{
        return movieService.getMovieReviews(movieId)
    }
}