package com.example.moviemaster.data.datasource

import com.example.moviemaster.data.model.Movie
import com.example.moviemaster.data.model.MovieResponse
import com.example.moviemaster.data.remote.MovieService
import io.reactivex.Single
import javax.inject.Inject

class MoviesSearchPagingSource@Inject constructor(private val movieService: MovieService, private val searchQuery: String ) : BasicPagingSource<Movie, MovieResponse>() {

    override fun getResponse(page: Int): Single<MovieResponse> {
        return movieService.getSearchedMovies(searchQuery, page)
    }

    override fun toLoadResult(response: MovieResponse): LoadResult<Int, Movie> {
        return LoadResult.Page(response.results, null, response.page + 1, LoadResult.Page.COUNT_UNDEFINED, LoadResult.Page.COUNT_UNDEFINED)
    }
}