package com.example.moviemaster.ui.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.moviemaster.R
import com.example.moviemaster.data.model.Genre
import com.example.moviemaster.databinding.ActivityMainBinding
import com.example.moviemaster.ui.adapter.GenreFilterAdapter
import com.example.moviemaster.ui.fragment.MovieListFragment
import com.example.moviemaster.viewmodel.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    @Inject lateinit var genreAdapter: GenreFilterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setBinding()
        getGenres()
        addSearchClickListener()
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

    private fun setGenreAdapter() {
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

    private fun addSearchClickListener() {
        binding.searchIcon.setOnClickListener {
            val searchedQuery = binding.searchBar.text.toString()
            if (searchedQuery.isNotEmpty()) {
                openMoviesFragment(searchedQuery)
                hideKeyboard()
            }
        }

        binding.searchBar.setOnEditorActionListener { editText, i, keyEvent ->
            if ((keyEvent != null && (keyEvent.keyCode == KeyEvent.KEYCODE_ENTER)) || (i == EditorInfo.IME_ACTION_DONE)) {
                openMoviesFragment(editText.text.toString())
            }
             false
        }
    }

    private fun openMoviesFragment(searchedQuery: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MovieListFragment.newInstance(searchedQuery))
            .addToBackStack(MovieListFragment::class.java.simpleName).commit()
        viewModel.isSearchOpened = true
    }

    private fun hideKeyboard(){
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.searchIcon.windowToken, 0)
    }
}