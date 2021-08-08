package com.example.moviemaster.viewmodel

import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.MutableLiveData
import com.example.moviemaster.data.Repository
import com.example.moviemaster.data.model.MovieResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieListViewModel(private val repository: Repository) : BaseViewModel(){

    var searchedQuery: String? = null
    var genre: Int = 0
    var page: Int = 1

    @get: Bindable
    var isLoading: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.loading)
        }

    private var moviesLiveData: MutableLiveData<MovieResponse> = MutableLiveData()
    private var searchedMoviesLiveData: MutableLiveData<MovieResponse> = MutableLiveData()

    fun getMovieLiveData(): MutableLiveData<MovieResponse> {
        return moviesLiveData
    }

    fun getSearchedMovieLiveData(): MutableLiveData<MovieResponse> {
        return searchedMoviesLiveData
    }

    fun getMovies(page: Int, genre: String) {
        val disposable =  repository.getMovies(page, genre)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ v -> moviesLiveData.postValue(v)}, {e -> Log.e("MovieListFragment", e.localizedMessage)})
        addDisposable(disposable)

    }

    fun getSearchedMovies(searchQuery: String, page: Int) {
        val disposable =  repository.getSearchedMovies(searchQuery, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ v -> searchedMoviesLiveData.postValue(v)}, {e -> Log.e("MovieListFragment", e.localizedMessage)})
        addDisposable(disposable)

    }
}