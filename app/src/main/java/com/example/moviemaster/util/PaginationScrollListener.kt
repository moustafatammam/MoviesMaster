package com.example.moviemaster.util

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(private var layoutManager: GridLayoutManager) :
    RecyclerView.OnScrollListener() {

    var visibleItemCount = 0;
    var totalItemCount = 0;
    var pastVisibleItems = 0;

    abstract fun isLoading(): Boolean

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        visibleItemCount = layoutManager.childCount
        totalItemCount = layoutManager.itemCount

        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        pastVisibleItems = firstVisibleItemPosition

        val visibleThreshold = 5

        if (!isLoading()) {
            if ((visibleItemCount + pastVisibleItems) >= totalItemCount - visibleThreshold) {
                loadMoreItems()
            }
        }
    }

    abstract fun loadMoreItems()
}