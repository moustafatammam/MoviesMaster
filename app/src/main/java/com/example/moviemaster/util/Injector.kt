package com.example.moviemaster.util

import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.moviemaster.data.Repository
import com.example.moviemaster.data.model.Movie
import com.example.moviemaster.data.remote.ApiClient
import com.example.moviemaster.viewmodel.MovieDetailsViewModel
import com.example.moviemaster.viewmodel.MovieDetailsViewModelFactory
import com.example.moviemaster.viewmodel.MovieListViewModel
import com.example.moviemaster.viewmodel.MovieListViewModelFactory

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

    fun getMovieDetailsViewModel(activity: ComponentActivity, movie: Movie): MovieDetailsViewModel{
        val movieDetailsViewModelFactory = MovieDetailsViewModelFactory(movie)
        return ViewModelProvider(activity, movieDetailsViewModelFactory).get(MovieDetailsViewModel::class.java)
    }

}