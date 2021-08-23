package com.example.moviemaster.di

import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import com.example.moviemaster.data.model.Movie
import com.example.moviemaster.ui.adapter.BasePagedDataAdapter
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


    @Provides
    fun provideMoviePagedAdapter(): BasePagedDataAdapter<Movie> {
        return BasePagedDataAdapter(object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

        })
    }
}