package com.example.moviemaster.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemaster.R
import com.example.moviemaster.data.model.Movie
import com.example.moviemaster.data.model.MovieResponse
import com.example.moviemaster.databinding.FragmentMovieListBinding
import com.example.moviemaster.ui.activity.MovieDetailsActivity
import com.example.moviemaster.ui.adapter.BaseAdapter
import com.example.moviemaster.util.ItemClickListener
import com.example.moviemaster.util.PaginationScrollListener
import com.example.moviemaster.viewmodel.MainViewModel
import com.example.moviemaster.viewmodel.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieListFragment() : BaseFragment<FragmentMovieListBinding>(), ItemClickListener {

    override val viewModel: MovieListViewModel by viewModels()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMovieListBinding = FragmentMovieListBinding::inflate

    private val mainViewModel: MainViewModel by activityViewModels()
    @Inject lateinit var moviesAdapter: BaseAdapter<Movie>

    companion object {
        fun newInstance(query: String): MovieListFragment {
            val args = Bundle()
            args.putString("searched_query", query)
            val fragment = MovieListFragment()
            fragment.arguments = args
            return fragment
        }

        fun newInstance(genre: Int): MovieListFragment {
            val args = Bundle()
            args.putInt("genre", genre)
            val fragment = MovieListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            viewModel.genre = it.getInt("genre")
            viewModel.searchedQuery = it.getString("searched_query")
        }

        setAdapter()
        addOnBackPressed()
        initRecyclerView()
        setOnSwipeRefreshLayout()
    }

    private fun setAdapter(){
        moviesAdapter.apply {
            id = BR.movie
            layoutId = R.layout.view_movie
            itemClickListener = this@MovieListFragment
            recyclerView = (binding as FragmentMovieListBinding).recycler
            setFooter(true)
        }
    }

    private fun addOnBackPressed(){
        if (mainViewModel.isSearchOpened) {
            activity?.onBackPressedDispatcher?.addCallback(
                this.viewLifecycleOwner,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        mainViewModel.isSearchOpened = false
                        parentFragmentManager.beginTransaction().remove(this@MovieListFragment)
                            .commit()
                    }
                })
        }
    }

    private fun initRecyclerView() {
        (binding as FragmentMovieListBinding).recycler.apply {
            layoutManager = GridLayoutManager(this@MovieListFragment.context, 2)
            setSpanCount(moviesAdapter, layoutManager as GridLayoutManager)
            setRecyclerOnScrollListener(this)
            adapter = moviesAdapter
        }
        updateMovies(1)
    }

    private fun setSpanCount(adapter: BaseAdapter<Movie>, gridLayoutManager: GridLayoutManager){
         gridLayoutManager.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup() {
             override fun getSpanSize(position: Int): Int {
                 return if (adapter.getItemViewType(position) == adapter.TYPE_FOOTER_LOADER) gridLayoutManager.spanCount else 1
             }
         }
    }

    private fun setRecyclerOnScrollListener(recyclerView: RecyclerView){
        recyclerView. addOnScrollListener(object : PaginationScrollListener(recyclerView.layoutManager as GridLayoutManager) {

            override fun isLoading(): Boolean {
                return viewModel.isFooterVisible
            }

            override fun loadMoreItems() {
                viewModel.isFooterVisible = true
                viewModel.page = viewModel.page + 1
                Handler(Looper.getMainLooper()).postDelayed(Runnable {
                    updateMovies(viewModel.page)
                }, 1000)
            }
        })

    }

    fun updateMovies(page: Int) {
        viewModel.isLoading = true
        viewModel.isFooterVisible = true

        if (viewModel.searchedQuery != null) {
            viewModel.getSearchedMovieLiveData().observe(this.viewLifecycleOwner, Observer {
                updateAdapter(it, page)
            })
            viewModel.getSearchedMovies(viewModel.searchedQuery!!, page)
        } else {
            viewModel.getMovieLiveData().observe(this.viewLifecycleOwner, Observer {
                updateAdapter(it, page)
            })
            viewModel.getMovies(page, if (viewModel.genre == 0) "" else viewModel.genre.toString())
        }
        (binding as FragmentMovieListBinding).swipeLayout.isRefreshing = false
    }

    private fun updateAdapter(movieResponse: MovieResponse, page: Int) {
        if (movieResponse.totalPages >= movieResponse.page && movieResponse.page == page) {
            if (page > 1) {
                moviesAdapter.addData(movieResponse.results)
            } else {
                moviesAdapter.updateData(movieResponse.results)
            }
        }
        viewModel.isLoading = false
        viewModel.isFooterVisible = false

    }

    override fun onItemClicked(item: Any?) {
        startActivity(MovieDetailsActivity.newInstance(this.context, item as Movie))
    }

    private fun setOnSwipeRefreshLayout() {
        (binding as FragmentMovieListBinding).swipeLayout.setOnRefreshListener {
            updateMovies(1)
        }
    }


}