package com.example.moviemaster.viewmodel

import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.MutableLiveData
import com.example.moviemaster.data.Repository
import com.example.moviemaster.data.model.Genre
import com.example.moviemaster.data.model.GenreResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {

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

    fun getGenres() {
        val disposable =  repository.getGenres()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ v -> genresLiveData.postValue(v) }, {e -> Log.e("MainActivity", e.localizedMessage)})

        addDisposable(disposable)
    }
}