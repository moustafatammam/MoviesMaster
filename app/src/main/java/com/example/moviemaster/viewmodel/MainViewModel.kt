package com.example.moviemaster.viewmodel

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviemaster.data.Repository
import com.example.moviemaster.data.model.Genre
import com.example.moviemaster.data.model.GenreResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val repository: Repository) : BaseViewModel() {

    private var genresLiveData: MutableLiveData<GenreResponse> = MutableLiveData()

    var genres: MutableList<Genre> = mutableListOf()

    @get:Bindable
    var isSearchOpened: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.searchOpened)
        }

    fun getGenreLiveData(): MutableLiveData<GenreResponse> {
        return genresLiveData
    }

    fun getGenres(): Disposable? {
        return repository.getGenres()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { v -> genresLiveData.postValue(v) }
    }
}