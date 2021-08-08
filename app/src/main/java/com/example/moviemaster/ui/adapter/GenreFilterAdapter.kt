package com.example.moviemaster.ui.adapter

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moviemaster.data.model.Genre
import com.example.moviemaster.ui.fragment.MovieListFragment

class GenreFilterAdapter(context: Context) : FragmentStateAdapter(context as AppCompatActivity) {

    var genres: MutableList<Genre> = mutableListOf()

    override fun getItemCount(): Int {
       return genres.size
    }

    override fun createFragment(position: Int): Fragment {
        return MovieListFragment.newInstance(genres[position].id)
    }
}