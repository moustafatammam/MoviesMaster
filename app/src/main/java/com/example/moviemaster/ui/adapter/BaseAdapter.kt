package com.example.moviemaster.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemaster.util.ItemClickListener
import javax.inject.Inject


open class BaseAdapter<T> @Inject constructor() :
    RecyclerView.Adapter<BaseAdapter.BaseViewHolder<T>>() {

    private var list = ArrayList<T>()
    var id: Int = 0
    var layoutId: Int = 0
    var itemClickListener: ItemClickListener<T>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, layoutId, parent, false)
        return BaseViewHolder(binding)
    }


    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(id, list[position], itemClickListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class BaseViewHolder<T>(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(id: Int, item: T, itemClickListener: ItemClickListener<T>?) {
            binding.setVariable(id, item)
            itemView.setOnClickListener {
                itemClickListener?.onItemClicked(item)
            }
            binding.executePendingBindings()
        }
    }

    fun addData(listItems: ArrayList<T>) {
        list.addAll(listItems)
        notifyDataSetChanged()
    }

    fun updateData(listItems: ArrayList<T>) {
        list.clear()
        list.addAll(listItems)
    }


}