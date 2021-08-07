package com.example.moviemaster.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.example.moviemaster.R

object BindingAdapter {

    @BindingAdapter("posterUrl")
    @JvmStatic
    fun loadPosterImage(view: ImageView, url: String?) {
        if (!url.isNullOrEmpty()) {
            Glide.with(view.context).load("https://image.tmdb.org/t/p/w500$url").into(view)
        }
    }

    @BindingAdapter("backdropUrl")
    @JvmStatic
    fun loadBackdropImage(view: ImageView, url: String?) {
        if (!url.isNullOrEmpty()) {
            Glide.with(view.context).load("https://image.tmdb.org/t/p/w780$url").into(view)
        }
    }
}