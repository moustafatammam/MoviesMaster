package com.example.moviemaster.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemaster.data.model.Movie
import com.example.moviemaster.databinding.ViewMovieBinding
import com.example.moviemaster.util.ItemClickListener

class MoviesAdapter(val itemClickListener: ItemClickListener): RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    var movieList = ArrayList<Movie>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewMovieBinding.inflate(inflater)
        return MoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) = holder.bind(movieList[position], itemClickListener)

    override fun getItemCount(): Int = movieList.size

    class MoviesViewHolder(private val binding: ViewMovieBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: Movie, itemClickListener: ItemClickListener){
            binding.movie = item
            binding.movieContainer.setOnClickListener {
                itemClickListener.onItemClicked(item)
            }
        }

    }

    fun addData(listItems: ArrayList<Movie>) {
        movieList.addAll(listItems)
    }

    fun updateData(listItems: ArrayList<Movie>) {
        movieList.clear()
        movieList.addAll(listItems)
    }

}