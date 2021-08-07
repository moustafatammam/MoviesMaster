package com.example.moviemaster.viewmodel

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.MutableLiveData
import com.example.moviemaster.data.Repository
import com.example.moviemaster.data.model.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
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

    fun getImages(): Disposable? {
        return repository.getMovieImages(movie?.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { v -> imagesLiveData.postValue(v) }
    }

    fun getCast(): Disposable? {
        return repository.getMovieCast(movie?.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { v -> castLiveData.postValue(v) }
    }

    fun getReviews(): Disposable? {
        return repository.getMovieReview(movie?.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { v -> reviewLiveData.postValue(v) }
    }


}