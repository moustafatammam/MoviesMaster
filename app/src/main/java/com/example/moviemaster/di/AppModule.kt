package com.example.moviemaster.di

import com.example.moviemaster.data.repository.MovieDetailsRepository
import com.example.moviemaster.data.remote.MovieService
import com.example.moviemaster.data.repository.MainRepository
import com.example.moviemaster.data.repository.MovieListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRepository(movieService: MovieService) = MovieDetailsRepository(movieService)

    @Singleton
    @Provides
    fun provideMovieListRepository(movieService: MovieService) = MovieListRepository(movieService)

    @Singleton
    @Provides
    fun provideMainRepository(movieService: MovieService) = MainRepository(movieService)
}