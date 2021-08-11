package com.example.moviemaster.di

import android.content.Context
import com.example.moviemaster.ui.adapter.GenreFilterAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped


@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {

    @Provides
    @ActivityScoped
    fun provideGenreAdapterFragmentState(@ActivityContext context: Context): GenreFilterAdapter {
        return GenreFilterAdapter(context)
    }
}