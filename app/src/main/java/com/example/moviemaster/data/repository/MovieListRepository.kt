package com.example.moviemaster.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.observable
import com.example.moviemaster.data.datasource.MoviesPagingSource
import com.example.moviemaster.data.datasource.MoviesSearchPagingSource
import com.example.moviemaster.data.model.Movie
import com.example.moviemaster.data.model.MovieResponse
import com.example.moviemaster.data.remote.MovieService
import io.reactivex.Observable
import javax.inject.Inject


private const val NETWORK_PAGE_SIZE  = 20


class MovieListRepository @Inject constructor(private val movieService: MovieService) {

    fun getMovies(genre: String): Observable<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                1,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviesPagingSource(movieService, genre) }
        ).observable
    }

    fun getSearchedMovies(searchQuery: String): Observable<PagingData<Movie>>{
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                1,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviesSearchPagingSource(movieService, searchQuery) }
        ).observable
    }

}