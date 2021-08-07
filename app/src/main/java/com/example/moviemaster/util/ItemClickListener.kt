package com.example.moviemaster.util

import com.example.moviemaster.data.model.Movie

interface ItemClickListener {

    fun onItemClicked(movie: Movie)

}