package com.example.moviemaster.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.moviemaster.R
import com.example.moviemaster.data.model.Image
import com.example.moviemaster.databinding.FragmentMovieImageBinding
import com.example.moviemaster.ui.adapter.MovieImagesAdapter
import com.example.moviemaster.util.Injector
import com.example.moviemaster.viewmodel.MovieDetailsViewModel

class MovieImageFragment : Fragment() {

    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var binding: FragmentMovieImageBinding

    lateinit var movieImagesAdapter: MovieImagesAdapter

    companion object {
        fun newInstance(image: Image): MovieImageFragment {
            val args = Bundle()
            args.putParcelable("image", image)
            val fragment = MovieImageFragment()
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
            inflater, R.layout.fragment_movie_image, container, false
        )

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            binding.image = it.getParcelable("image")!!

        }


    }
}