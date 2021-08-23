package com.example.moviemaster.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviemaster.R
import com.example.moviemaster.data.model.Movie
import com.example.moviemaster.databinding.FragmentMovieListBinding
import com.example.moviemaster.ui.activity.MovieDetailsActivity
import com.example.moviemaster.ui.adapter.BasePagedDataAdapter
import com.example.moviemaster.ui.adapter.PagedStateAdapter
import com.example.moviemaster.util.ItemClickListener
import com.example.moviemaster.viewmodel.MainViewModel
import com.example.moviemaster.viewmodel.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MovieListFragment : BaseFragment<FragmentMovieListBinding>(), ItemClickListener {

    override val viewModel: MovieListViewModel by viewModels()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMovieListBinding = FragmentMovieListBinding::inflate

    private val mainViewModel: MainViewModel by activityViewModels()
    @Inject lateinit var moviesListAdapter: BasePagedDataAdapter<Movie>
    @Inject lateinit var loadStateAdapter: PagedStateAdapter


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
        moviesListAdapter.apply {
            id = BR.movie
            layoutId = R.layout.view_movie
            itemClickListener = this@MovieListFragment
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
            setSpanCount(moviesListAdapter, layoutManager as GridLayoutManager)
            adapter = moviesListAdapter.withLoadStateFooter(PagedStateAdapter())
        }
        updateMovies()
    }

    private fun setSpanCount(adapter: BasePagedDataAdapter<Movie>, gridLayoutManager: GridLayoutManager){
         gridLayoutManager.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup() {
             override fun getSpanSize(position: Int): Int {
                 return if (adapter.getItemViewType(position) == adapter.TYPE_FOOTER_LOADER) 1 else 2
             }
         }
    }

    private fun updateMovies() {
        viewModel.isLoading = true

        if (viewModel.searchedQuery != null) {
            viewModel.getSearchedMovieLiveData().observe(this.viewLifecycleOwner, Observer {
                viewLifecycleOwner.lifecycleScope.launch {
                    moviesListAdapter.submitData(it)
                    (binding as FragmentMovieListBinding).swipeLayout.isRefreshing = false
                }
                viewModel.isLoading = false
            })
            viewModel.getSearchedMovies(viewModel.searchedQuery!!)
        } else {
            viewModel.getMovieLiveData().observe(this.viewLifecycleOwner, Observer {
                viewLifecycleOwner.lifecycleScope.launch {
                    moviesListAdapter.submitData(it)
                    (binding as FragmentMovieListBinding).swipeLayout.isRefreshing = false
                }

                viewModel.isLoading = false
            })
            viewModel.getMovies(if (viewModel.genre == 0) "" else viewModel.genre.toString())
        }
    }

    override fun onItemClicked(item: Any?) {
        startActivity(MovieDetailsActivity.newInstance(this.context, item as Movie))
    }

    private fun setOnSwipeRefreshLayout() {
        (binding as FragmentMovieListBinding).swipeLayout.apply {
            setOnRefreshListener {
                moviesListAdapter.refresh()
            }
        }
    }

}