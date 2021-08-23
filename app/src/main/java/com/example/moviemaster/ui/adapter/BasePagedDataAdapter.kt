package com.example.moviemaster.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemaster.data.model.Movie
import com.example.moviemaster.ui.holder.BaseViewHolder
import com.example.moviemaster.util.ItemClickListener
import javax.inject.Inject


open class BasePagedDataAdapter<T : Any> @Inject constructor(diff: DiffUtil.ItemCallback<T>) : PagingDataAdapter<T, RecyclerView.ViewHolder>(diff){
     val TYPE_NORMAL = 0
     val TYPE_FOOTER_LOADER = 1

     var id: Int = 0
     var layoutId: Int = 0
     var itemClickListener: ItemClickListener? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BaseViewHolder<*> -> holder.bind(id, getItem(position), itemClickListener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == itemCount) TYPE_NORMAL else TYPE_FOOTER_LOADER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return BaseViewHolder<Movie>(DataBindingUtil.inflate(inflater, layoutId, parent, false))
    }
}