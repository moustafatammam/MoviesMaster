package com.example.moviemaster.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemaster.data.model.Cast
import com.example.moviemaster.databinding.ViewCastBinding
import javax.inject.Inject


class CastsAdapter @Inject constructor(): RecyclerView.Adapter<CastsAdapter.CastViewHolder>() {

    var castList = ArrayList<Cast>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewCastBinding.inflate(inflater)
        return CastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) = holder.bind(castList[position])

    override fun getItemCount(): Int = castList.size

    class CastViewHolder(private val binding: ViewCastBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: Cast){
            binding.cast = item
        }

    }

    fun addData(listItems: ArrayList<Cast>) {
        castList.addAll(listItems)
        notifyDataSetChanged()
    }

}