package com.example.moviemaster.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.example.moviemaster.data.model.Movie
import com.example.moviemaster.data.repository.MovieListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val movieListRepository: MovieListRepository) : BaseViewModel(){

    var searchedQuery: String? = null
    var genre: Int = 0

    private var moviesLiveData: MutableLiveData<PagingData<Movie>> = MutableLiveData()
    private var searchedMoviesLiveData: MutableLiveData<PagingData<Movie>> = MutableLiveData()

    fun getMovieLiveData(): MutableLiveData<PagingData<Movie>> {
        return moviesLiveData
    }

    fun getSearchedMovieLiveData(): MutableLiveData<PagingData<Movie>> {
        return searchedMoviesLiveData
    }

    fun getMovies(genre: String) {
        val disposable =  movieListRepository.getMovies(genre)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ v -> moviesLiveData.postValue(v)}, {e -> Log.e("MovieListFragment", e.localizedMessage)})
        addDisposable(disposable)
    }

    fun getSearchedMovies(searchQuery: String) {
        val disposable =  movieListRepository.getSearchedMovies(searchQuery)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ v -> searchedMoviesLiveData.postValue(v)}, {e -> Log.e("MovieListFragment", e.localizedMessage)})
        addDisposable(disposable)
    }
}