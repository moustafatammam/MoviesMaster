package com.example.moviemaster.data.remote

import com.example.moviemaster.data.model.GenreResponse
import com.example.moviemaster.data.model.MovieResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("discover/movie")
    fun getMovies(@Query("page") page: Int, @Query("with_genres") genre: String): Observable<MovieResponse>

    @GET("genre/movie/list")
    fun getGenres(): Observable<GenreResponse>

    @GET("search/movie")
    fun getSearchedMovies(@Query("query") query: String, @Query("page") page: Int): Observable<MovieResponse>
}