package com.example.moviebox.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import com.example.moviebox.R
import com.example.moviebox.data.Movie
import com.example.moviebox.data.MovieDatabase
import com.example.moviebox.databinding.FragmentMovieDescriptionBinding

class MovieDescriptionFragment : Fragment() {

    // UI
    private lateinit var binding: FragmentMovieDescriptionBinding
    private lateinit var starButtons: Array<Button>

    // Data
    private lateinit var movie: Movie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieDescriptionBinding.inflate(inflater, container, false)

        starButtons = arrayOf(
            binding.starOne,
            binding.starTwo,
            binding.starThree,
            binding.starFour,
            binding.starFive
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val movieId = MovieDescriptionFragmentArgs.fromBundle(it).movieId
            movie = MovieDatabase.getMovie(movieId)!!

            // Add Click Listeners
            binding.starOne.setOnClickListener { onStarClick( 1) }
            binding.starTwo.setOnClickListener { onStarClick( 2) }
            binding.starThree.setOnClickListener { onStarClick( 3) }
            binding.starFour.setOnClickListener { onStarClick( 4) }
            binding.starFive.setOnClickListener { onStarClick(5) }
            binding.buttonWatchLater.setOnClickListener{ onWatchLaterClick() }

            updateUI()
        }
    }

    private fun updateUI() {
        binding.movieImage.setImageResource(movie.imageUrl)
        binding.movieName.text = movie.name
        binding.movieDirector.text = movie.director
        binding.movieYear.text = movie.year.toString()
        binding.movieDescription.text = movie.description

        if (movie.rating !== null) {
            updateStarImages()
        }

        updateWatchLaterImage()
    }

    private fun updateStarImages() {
        val rating = if(movie.rating !== null) movie.rating else 0

        // Fill stars until rating
        for (i in 0 until rating!!) {
            starButtons[i].background = ContextCompat.getDrawable(requireContext(), R.drawable.ic_star_24)
        }

        // Clear other stars
        for (i in rating  until 5) {
            starButtons[i].background = ContextCompat.getDrawable(requireContext(), R.drawable.ic_star_outline_24)
        }
    }

    private fun updateWatchLaterImage() {
        val inWatchList = MovieDatabase.isInWatchList(movie.id)

        val backgroundDrawable: Int = if (inWatchList) {
            R.drawable.ic_watch_later_24
        } else {
            R.drawable.ic_watch_later_outline_24
        }

        binding.buttonWatchLater.background = ContextCompat.getDrawable(requireContext(), backgroundDrawable)
    }

    private fun onStarClick(rating: Int) {
        // Update rating
        MovieDatabase.updateMovieRating(movie, rating)
        updateStarImages()

        // Remove from watch list
        MovieDatabase.removeFromWatchList(movie)
        updateWatchLaterImage()
    }

    private fun onWatchLaterClick() {
        // Update watch list
        val inWatchList = MovieDatabase.isInWatchList(movie.id)

        if (inWatchList) {
            MovieDatabase.removeFromWatchList(movie)
        } else {
            MovieDatabase.addToWatchList(movie)
        }

        updateWatchLaterImage()

        // Remove rating
        MovieDatabase.updateMovieRating(movie, null)
        updateStarImages()
    }
}