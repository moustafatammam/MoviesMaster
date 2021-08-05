package com.example.moviemaster.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moviemaster.data.model.Genre
import com.example.moviemaster.ui.fragment.MovieListFragment

class GenreFilterAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    var genres: MutableList<Genre> = mutableListOf()

    override fun getItemCount(): Int {
       return genres.size
    }

    override fun createFragment(position: Int): Fragment {
        return MovieListFragment.newInstance(genres[position].id)
    }
}