package com.example.moviemaster.ui.adapter

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moviemaster.data.model.Image
import com.example.moviemaster.ui.fragment.MovieImageFragment

class MovieImagesAdapter(context: Context) : FragmentStateAdapter(context as AppCompatActivity) {

    var images: MutableList<Image> = mutableListOf()

    override fun getItemCount(): Int {
        return images.size
    }

    override fun createFragment(position: Int): Fragment {
        return MovieImageFragment.newInstance(images[position])
    }
}