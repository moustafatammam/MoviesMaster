package com.example.moviemaster.data.datasource

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

private const val STARTING_PAGE_INDEX = 1

abstract class BasicPagingSource<T : Any, V>: RxPagingSource<Int, T>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, T>> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX
        val response = getResponse(pageIndex)
        return response.subscribeOn(Schedulers.io())
            .map(this::toLoadResult)
            .onErrorReturn { e -> LoadResult.Error(e) }

    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    abstract fun getResponse(page: Int): Single<V>
    abstract fun toLoadResult(response: V): LoadResult<Int, T>

}