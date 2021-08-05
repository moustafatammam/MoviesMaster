package com.example.moviemaster.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviemaster.data.Repository
import com.example.moviemaster.data.model.GenreResponse
import com.example.moviemaster.data.model.MovieResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MovieListViewModel(private val repository: Repository): ViewModel()  {

    private var moviesLiveData: MutableLiveData<MovieResponse> = MutableLiveData()
    private var genresLiveData: MutableLiveData<GenreResponse> = MutableLiveData()
    private var searchedMoviesLiveData: MutableLiveData<MovieResponse> = MutableLiveData()



    fun getMovies(page: Int): Disposable? {
        return repository.getMovies(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { v -> moviesLiveData.postValue(v) }
    }

    fun getGenres(): Disposable? {
        return repository.getGenres()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { v -> genresLiveData.postValue(v) }
    }

    fun getSearchedMovies(searchQuery: String, page: Int): Disposable? {
        return repository.getSearchedMovies(searchQuery, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { v -> searchedMoviesLiveData.postValue(v) }
    }
}