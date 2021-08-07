package com.example.moviemaster.util

import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.moviemaster.data.Repository
import com.example.moviemaster.data.model.Movie
import com.example.moviemaster.data.remote.ApiClient
import com.example.moviemaster.viewmodel.*

class Injector {

    private val movieService = ApiClient.buildMoviesService()
    private val repository = Repository(movieService)

    fun getMovieListViewModel(fragment: Fragment): MovieListViewModel{
        val movieListViewModelFactory = MovieListViewModelFactory(repository)
        return ViewModelProvider(fragment, movieListViewModelFactory).get(MovieListViewModel::class.java)
    }

    fun getMovieListViewModel(activity: ComponentActivity): MovieListViewModel{
        val movieListViewModelFactory = MovieListViewModelFactory(repository)
        return ViewModelProvider(activity, movieListViewModelFactory).get(MovieListViewModel::class.java)
    }

    fun getMainViewModel(activity: ComponentActivity): MainViewModel{
        val mainViewModelFactory = MainViewModelFactory(repository)
        return ViewModelProvider(activity, mainViewModelFactory).get(MainViewModel::class.java)
    }

    fun getMovieDetailsViewModel(activity: ComponentActivity, movie: Movie?): MovieDetailsViewModel{
        val movieDetailsViewModelFactory = MovieDetailsViewModelFactory(movie,repository)
        return ViewModelProvider(activity, movieDetailsViewModelFactory).get(MovieDetailsViewModel::class.java)
    }

}