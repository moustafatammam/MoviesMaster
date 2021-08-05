package com.example.moviemaster.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemaster.R
import com.example.moviemaster.databinding.FragmentMovieListBinding
import com.example.moviemaster.ui.adapter.MoviesAdapter
import com.example.moviemaster.util.Injector
import com.example.moviemaster.viewmodel.MovieListViewModel

class MovieListFragment() : Fragment() {

    private lateinit var viewModel: MovieListViewModel
    private lateinit var binding: FragmentMovieListBinding
    lateinit var moviesAdapter: MoviesAdapter

    companion object{
        fun newInstance(query: String): MovieListFragment{
            val args = Bundle()
            args.putString("searched_query", query)
            val fragment = MovieListFragment()
            fragment.arguments = args
            return fragment
        }

        fun newInstance(genre: Int): MovieListFragment{
            val args = Bundle()
            args.putInt("genre", genre)
            val fragment = MovieListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

         binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = Injector().getMovieListViewModel(this)

        arguments?.let {
            viewModel.genre = it.getInt("genre")
            viewModel.searchedQuery = it.getString("searched_query")
        }
        initRecyclerView()
        updateMovies(1)
    }

    fun initRecyclerView() {
        binding.recycler.apply {
            layoutManager = GridLayoutManager(this@MovieListFragment.context, 2)
            addOnScrollListener(object :
                PaginationScrollListener(layoutManager as GridLayoutManager) {

                override fun isLoading(): Boolean {
                    return viewModel.isLoading
                }

                override fun loadMoreItems() {
                    viewModel.isLoading = true
                    viewModel.page = viewModel.page + 1
                    updateMovies(viewModel.page)
                    post(Runnable { adapter?.notifyDataSetChanged() })
                }
            })
            moviesAdapter = MoviesAdapter()
            adapter = moviesAdapter
        }
    }

    fun updateMovies(page: Int){
        viewModel.isLoading = true
        viewModel.getMovieLiveData().observe(this.viewLifecycleOwner, Observer{
            if (it != null){
                if (it.totalPages >= it.page && it.page == page) {
                    if (page > 1) {
                        moviesAdapter.addData(it.results)
                    } else {
                        moviesAdapter.updateData(it.results)
                        moviesAdapter.notifyDataSetChanged()
                    }
                }
                viewModel.isLoading = false

            }
        })
        viewModel.getMovies(page, if(viewModel.genre == 0) "" else viewModel.genre.toString());
    }

    abstract class PaginationScrollListener
        (var layoutManager: GridLayoutManager) : RecyclerView.OnScrollListener() {

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
}