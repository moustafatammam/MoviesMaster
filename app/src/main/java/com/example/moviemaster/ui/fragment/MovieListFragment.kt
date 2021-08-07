package com.example.moviemaster.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviemaster.R
import com.example.moviemaster.data.model.MovieResponse
import com.example.moviemaster.databinding.FragmentMovieListBinding
import com.example.moviemaster.ui.adapter.MoviesAdapter
import com.example.moviemaster.util.Injector
import com.example.moviemaster.util.PaginationScrollListener
import com.example.moviemaster.viewmodel.MainViewModel
import com.example.moviemaster.viewmodel.MovieListViewModel

class MovieListFragment() : Fragment() {

    private lateinit var viewModel: MovieListViewModel
    private lateinit var mainViewModel: MainViewModel

    private lateinit var binding: FragmentMovieListBinding
    lateinit var moviesAdapter: MoviesAdapter

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_movie_list, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = Injector().getMainViewModel(requireActivity())
        viewModel = Injector().getMovieListViewModel(this)


        arguments?.let {
            viewModel.genre = it.getInt("genre")
            viewModel.searchedQuery = it.getString("searched_query")
        }

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

        initRecyclerView()
        updateMovies(1)
    }

    private fun initRecyclerView() {
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


    fun updateMovies(page: Int) {
        viewModel.isLoading = true

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
    }

    private fun updateAdapter(movieResponse: MovieResponse, page: Int) {
        if (movieResponse.totalPages >= movieResponse.page && movieResponse.page == page) {
            if (page > 1) {
                moviesAdapter.addData(movieResponse.results)
            } else {
                moviesAdapter.updateData(movieResponse.results)
                moviesAdapter.notifyDataSetChanged()
            }
        }
        viewModel.isLoading = false

    }
}