package com.example.moviemaster.data.repository

import com.example.moviemaster.data.model.*
import com.example.moviemaster.data.remote.MovieService
import io.reactivex.Observable
import javax.inject.Inject

class MovieDetailsRepository@Inject constructor(private val movieService: MovieService){

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