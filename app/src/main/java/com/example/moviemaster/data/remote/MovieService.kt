package com.example.moviemaster.data.remote

import com.example.moviemaster.data.model.CastResponse
import com.example.moviemaster.data.model.GenreResponse
import com.example.moviemaster.data.model.ImageResponse
import com.example.moviemaster.data.model.MovieResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.example.moviemaster.data.model.ReviewResponse
import io.reactivex.Single


interface MovieService {

    @GET("discover/movie")
    fun getMovies(
        @Query("page") page: Int,
        @Query("with_genres") genre: String
    ): Single<MovieResponse>

    @GET("genre/movie/list")
    fun getGenres(): Observable<GenreResponse>

    @GET("search/movie")
    fun getSearchedMovies(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Single<MovieResponse>

    @GET("movie/{movie_id}/images")
    fun getMovieImages(@Path("movie_id") id: Int?): Observable<ImageResponse>

    @GET("movie/{movie_id}/credits")
    fun getMovieCast(
        @Path("movie_id") id: Int?
    ): Observable<CastResponse>

    @GET("movie/{movie_id}/reviews")
    fun getMovieReviews(
        @Path("movie_id") id: Int?
    ): Observable<ReviewResponse>

}