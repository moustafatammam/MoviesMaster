package com.example.moviemaster.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moviemaster.data.model.Image
import com.example.moviemaster.ui.fragment.MovieImageFragment

class MovieImagesAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    var images: MutableList<Image> = mutableListOf()

    override fun getItemCount(): Int {
        return images.size
    }

    override fun createFragment(position: Int): Fragment {
        return MovieImageFragment.newInstance(images[position])
    }
}