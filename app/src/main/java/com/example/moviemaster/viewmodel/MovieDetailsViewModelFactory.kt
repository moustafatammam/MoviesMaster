package com.example.moviemaster.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviemaster.data.model.Movie

class MovieDetailsViewModelFactory(private val movie: Movie): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
            return MovieDetailsViewModel(movie) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}