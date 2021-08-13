package com.example.moviemaster.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemaster.R
import com.example.moviemaster.ui.holder.BaseViewHolder
import com.example.moviemaster.util.ItemClickListener
import javax.inject.Inject


open class BaseAdapter<T> @Inject constructor() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val TYPE_NORMAL = 0
    val TYPE_FOOTER_LOADER = 1

    var recyclerView: RecyclerView? = null

    var list = ArrayList<T>()
    var id: Int = 0
    var layoutId: Int = 0
    var itemClickListener: ItemClickListener? = null
    private var withFooter: Boolean = false


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {

            TYPE_FOOTER_LOADER ->  FooterViewHolder(inflater.inflate(R.layout.view_footer_loader, parent, false))
            else ->  BaseViewHolder<T>(DataBindingUtil.inflate(inflater, layoutId, parent, false))

        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (withFooter && position >= list.size) {
            TYPE_FOOTER_LOADER
        } else {
            TYPE_NORMAL
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BaseViewHolder<*> -> holder.bind(id, list[position], itemClickListener)
        }
    }


    override fun getItemCount(): Int {
        var itemCount = list.size
        if (withFooter) itemCount++
        return itemCount
    }

    fun addData(listItems: ArrayList<T>) {
        list.addAll(listItems)
        notifyDataSetChanged()
    }

    fun updateData(listItems: ArrayList<T>) {
        list.clear()
        list.addAll(listItems)
        notifyDataSetChanged()
    }

    fun setFooter(withFooter: Boolean) {
        this.withFooter = withFooter
    }

    class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view)


}