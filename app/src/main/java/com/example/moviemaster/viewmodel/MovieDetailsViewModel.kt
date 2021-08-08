package com.example.moviemaster.viewmodel

import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.MutableLiveData
import com.example.moviemaster.data.Repository
import com.example.moviemaster.data.model.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieDetailsViewModel(val movie: Movie?, private val repository: Repository) :
    BaseViewModel() {

    @get: Bindable
    var castSize: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.castSize)
        }

    @get: Bindable
    var reviewSize: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.reviewSize)
        }

    lateinit var image: Image

    @get: Bindable
    var isLoading: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.loading)
        }

    private var imagesLiveData: MutableLiveData<ImageResponse> = MutableLiveData()
    private var castLiveData: MutableLiveData<CastResponse> = MutableLiveData()
    private var reviewLiveData: MutableLiveData<ReviewResponse> = MutableLiveData()


    fun getImagesLiveData(): MutableLiveData<ImageResponse> {
        return imagesLiveData
    }

    fun getCastLiveData(): MutableLiveData<CastResponse> {
        return castLiveData
    }

    fun getReviewLiveData(): MutableLiveData<ReviewResponse> {
        return reviewLiveData
    }

    fun getImages() {
        val disposable =  repository.getMovieImages(movie?.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ v -> imagesLiveData.postValue(v)}, {e -> Log.e("MovieDetailsActivity", e.localizedMessage)})
        addDisposable(disposable)
    }

    fun getCast() {
        val disposable = repository.getMovieCast(movie?.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ v -> castLiveData.postValue(v)}, {e -> Log.e("MovieDetailsActivity", e.localizedMessage)})
        addDisposable(disposable)
    }

    fun getReviews() {
        val disposable = repository.getMovieReview(movie?.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ v -> reviewLiveData.postValue(v)}, {e -> Log.e("MovieDetailsActivity", e.localizedMessage)})
        addDisposable(disposable)
    }


}