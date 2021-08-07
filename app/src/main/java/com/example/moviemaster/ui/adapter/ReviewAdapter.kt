package com.example.moviemaster.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemaster.data.model.Cast
import com.example.moviemaster.data.model.Review
import com.example.moviemaster.databinding.ViewReviewBinding

class ReviewAdapter: RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    var reviewList = ArrayList<Review>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewReviewBinding.inflate(inflater)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) = holder.bind(reviewList[position])

    override fun getItemCount(): Int = reviewList.size

    class ReviewViewHolder(private val binding: ViewReviewBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: Review){
            binding.review = item
        }

    }

    fun addData(listItems: ArrayList<Review>) {
        reviewList.addAll(listItems)
        notifyDataSetChanged()
    }
}