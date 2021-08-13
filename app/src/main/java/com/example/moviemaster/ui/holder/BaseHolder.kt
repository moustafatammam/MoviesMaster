package com.example.moviemaster.ui.holder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemaster.util.ItemClickListener

open class BaseViewHolder<T>(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(id: Int, item: Any?, itemClickListener: ItemClickListener?) {
        binding.setVariable(id, item)
        itemView.setOnClickListener {
            itemClickListener?.onItemClicked(item as T)
        }
        binding.executePendingBindings()
    }

}