package com.example.moviemaster.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemaster.ui.holder.BaseViewHolder
import com.example.moviemaster.util.ItemClickListener
import javax.inject.Inject


open class BaseAdapter<T> @Inject constructor() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var recyclerView: RecyclerView? = null

    var list = ArrayList<T>()
    var id: Int = 0
    var layoutId: Int = 0
    var itemClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return BaseViewHolder<T>(DataBindingUtil.inflate(inflater, layoutId, parent, false))
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BaseViewHolder<*> -> holder.bind(id, list[position], itemClickListener)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addData(listItems: ArrayList<T>) {
        list.addAll(listItems)
        notifyDataSetChanged()
    }

}