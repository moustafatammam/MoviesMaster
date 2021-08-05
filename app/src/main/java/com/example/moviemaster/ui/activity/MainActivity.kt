package com.example.moviemaster.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.moviemaster.R
import com.example.moviemaster.data.model.Genre
import com.example.moviemaster.databinding.ActivityMainBinding
import com.example.moviemaster.ui.adapter.GenreFilterAdapter
import com.example.moviemaster.ui.fragment.MovieListFragment
import com.example.moviemaster.util.Injector
import com.example.moviemaster.viewmodel.MovieListViewModel
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MovieListViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var genreAdapter: GenreFilterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = Injector().getMovieListViewModel(this)
        setBinding()
        getGenres()
    }

    private fun getGenres() {
        viewModel.apply {
            getGenres()
            getGenreLiveData().observe(this@MainActivity, Observer {
                viewModel.genres.apply {
                    add(Genre(0, "All"))
                    addAll(it.genres)
                }

                setGenreAdapter()
                setTabs(viewModel.genres)
            })
        }
    }

    private fun setGenreAdapter(){
        genreAdapter = GenreFilterAdapter(supportFragmentManager, lifecycle)
        binding.pager.adapter = genreAdapter
        genreAdapter.genres = viewModel.genres
    }

    private fun setBinding() {
        binding.viewmodel = viewModel
        binding.executePendingBindings()
    }

    private fun setTabs(genres: List<Genre>) {
        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, binding.pager) { tab, position ->
            tab.text = genres[position].genre
        }.attach()
    }
}