package com.example.moviemaster.di

import com.example.moviemaster.data.Repository
import com.example.moviemaster.data.remote.MovieService
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
    fun provideRepository(movieService: MovieService) = Repository(movieService)
}