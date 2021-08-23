package com.example.moviemaster.data.datasource

import androidx.paging.PagingSource.LoadResult.Page.Companion.COUNT_UNDEFINED
import com.example.moviemaster.data.model.Movie
import com.example.moviemaster.data.model.MovieResponse
import com.example.moviemaster.data.remote.MovieService
import io.reactivex.Single
import javax.inject.Inject

class MoviesPagingSource @Inject constructor(private val movieService: MovieService, private val genre: String ) : BasicPagingSource<Movie, MovieResponse>() {

    override fun getResponse(page: Int): Single<MovieResponse> {
        return movieService.getMovies(page, genre)
    }

    override fun toLoadResult(response: MovieResponse): LoadResult<Int, Movie> {
        return LoadResult.Page(response.results, null, response.page + 1, COUNT_UNDEFINED, COUNT_UNDEFINED)
    }
}