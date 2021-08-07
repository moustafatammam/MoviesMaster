package com.example.moviemaster.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemaster.R
import com.example.moviemaster.data.model.Cast
import com.example.moviemaster.data.model.Image
import com.example.moviemaster.data.model.Movie
import com.example.moviemaster.data.model.Review
import com.example.moviemaster.databinding.ActivityMovieDetailsBinding
import com.example.moviemaster.ui.adapter.CastsAdapter
import com.example.moviemaster.ui.adapter.MovieImagesAdapter
import com.example.moviemaster.ui.adapter.ReviewAdapter
import com.example.moviemaster.util.Injector
import com.example.moviemaster.viewmodel.MovieDetailsViewModel

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var imagesAdapter: MovieImagesAdapter
    private lateinit var castsAdapter: CastsAdapter
    private lateinit var reviewAdapter: ReviewAdapter


    companion object {

        fun newInstance(context: Context?, movie: Movie): Intent {
            val intent: Intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra("extra_movie", movie)
            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)
        val movie: Movie? = intent.getParcelableExtra<Movie>("extra_movie")

        setViewModel(movie)
        setToolbar()
        getImages()
        iniRecyclerViews()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed();
        }
        return true
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setViewModel(movie: Movie?) {
        viewModel = Injector().getMovieDetailsViewModel(this, movie)
        binding.viewmodel = viewModel
    }

    private fun getImages() {
        viewModel.apply {
            isLoading = true
            getImages()
            getImagesLiveData().observe(this@MovieDetailsActivity, Observer {
                setImagesAdapter(it.backdrops)
                isLoading = false
            })
        }
    }

    private fun setImagesAdapter(images: List<Image>) {
        imagesAdapter = MovieImagesAdapter(supportFragmentManager, lifecycle)
        binding.pager.adapter = imagesAdapter
        imagesAdapter.images = images as MutableList<Image>
    }

    private fun initReviewRecyclerView() {
        reviewAdapter = ReviewAdapter()
        binding.reviewRecycler.apply {
            layoutManager =
                LinearLayoutManager(this@MovieDetailsActivity)
            adapter = reviewAdapter
        }
    }

    private fun updateReviews() {
        viewModel.getReviewLiveData().observe(this, Observer {
            viewModel.reviewSize = it.reviews.size
            reviewAdapter.addData(it.reviews as ArrayList<Review>)
        })
        viewModel.getReviews()
    }

    private fun initCastRecyclerView() {
        castsAdapter = CastsAdapter()
        binding.castRecycler.apply {
            layoutManager =
                LinearLayoutManager(this@MovieDetailsActivity, RecyclerView.HORIZONTAL, false)
            adapter = castsAdapter
        }
    }

    private fun updateCast() {
        viewModel.getCastLiveData().observe(this, Observer {
            viewModel.castSize = it.cast.size
            castsAdapter.addData(it.cast as ArrayList<Cast>)
        })
        viewModel.getCast()
    }

    private fun iniRecyclerViews() {
        initCastRecyclerView()
        initReviewRecyclerView()
        updateCast()
        updateReviews()
    }
}
