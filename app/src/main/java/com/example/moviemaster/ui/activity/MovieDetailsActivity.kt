package com.example.moviemaster.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemaster.R
import com.example.moviemaster.data.model.Cast
import com.example.moviemaster.data.model.Image
import com.example.moviemaster.data.model.Movie
import com.example.moviemaster.data.model.Review
import com.example.moviemaster.databinding.ActivityMovieDetailsBinding
import com.example.moviemaster.ui.adapter.BaseAdapter
import com.example.moviemaster.viewmodel.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsActivity : BaseActivity<ActivityMovieDetailsBinding>() {

    override val viewModel: MovieDetailsViewModel by viewModels()

    @Inject lateinit var castsAdapter: BaseAdapter<Cast>
    @Inject lateinit var reviewAdapter: BaseAdapter<Review>
    @Inject lateinit var imageAdapter: BaseAdapter<Image>


    override val bindingInflater: (LayoutInflater) -> ActivityMovieDetailsBinding = ActivityMovieDetailsBinding::inflate


    override fun setLayoutResourceId(): Int {
        return R.layout.activity_movie_details
    }

    companion object {

        fun newInstance(context: Context?, movie: Movie): Intent {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra("extra_movie", movie)
            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val movie: Movie? = intent.getParcelableExtra<Movie>("extra_movie")
        viewModel.isLoading = true

        setToolbar()
        setAdapters()
        setMovie(movie)
        iniRecyclerViews()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed();
        }
        return true
    }

    private fun setToolbar() {
        setSupportActionBar((binding as ActivityMovieDetailsBinding).toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setAdapters(){
        castsAdapter.apply {
            id = BR.cast
            layoutId = R.layout.view_cast
        }

        reviewAdapter.apply {
            id = BR.review
            layoutId = R.layout.view_review
        }

        imageAdapter.apply {
            id = BR.image
            layoutId = R.layout.view_image
        }
    }

    private fun setMovie(movie: Movie?) {
        viewModel.movie = movie!!
    }

    private fun initImagesRecyclerView() {
        (binding as ActivityMovieDetailsBinding).pager.apply {
            layoutManager =
                LinearLayoutManager(this@MovieDetailsActivity, RecyclerView.HORIZONTAL, false)
            adapter = imageAdapter
        }
    }

    private fun initCastRecyclerView() {
        (binding as ActivityMovieDetailsBinding).castRecycler.apply {
            layoutManager =
                LinearLayoutManager(this@MovieDetailsActivity, RecyclerView.HORIZONTAL, false)
            adapter = castsAdapter
        }
    }

    private fun initReviewRecyclerView() {
        (binding as ActivityMovieDetailsBinding).reviewRecycler.apply {
            layoutManager =
                LinearLayoutManager(this@MovieDetailsActivity)
            adapter = reviewAdapter
        }
    }

    private fun updateImages() {
        viewModel.getImagesLiveData().observe(this, Observer {
            imageAdapter.addData(it.backdrops as ArrayList<Image>)
            viewModel.isLoading = false
        })
        viewModel.getImages()
    }

    private fun updateReviews() {
        viewModel.getReviewLiveData().observe(this, Observer {
            viewModel.reviewSize = it.reviews.size
            reviewAdapter.addData(it.reviews as ArrayList<Review>)
            viewModel.isLoading = false
        })
        viewModel.getReviews()
    }

    private fun updateCast() {
        viewModel.getCastLiveData().observe(this, Observer {
            viewModel.castSize = it.cast.size
            castsAdapter.addData(it.cast as ArrayList<Cast>)
            viewModel.isLoading = false
        })
        viewModel.getCast()
    }

    private fun iniRecyclerViews() {
        initCastRecyclerView()
        initReviewRecyclerView()
        initImagesRecyclerView()
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            updateCast()
            updateReviews()
            updateImages()
        }, 1000)


    }


}
