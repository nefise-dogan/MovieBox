package com.example.moviebox.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviebox.data.Movie
import com.example.moviebox.data.MovieDatabase
import com.example.moviebox.databinding.FragmentWatchedBinding
import com.example.moviebox.ui.adapter.MovieListAdapter

class WatchedFragment : Fragment() {
    private lateinit var binding: FragmentWatchedBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieList: ArrayList<Movie>
    private lateinit var movieListAdapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWatchedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        movieList = MovieDatabase.getMoviesWithRating()

        movieListAdapter = MovieListAdapter(movieList) {
            val action =
                WatchedFragmentDirections.actionWatchedFragmentToMovieDescriptionFragment(
                    it.id
                )
            Navigation.findNavController(view).navigate(action)
        }

        recyclerView.adapter = movieListAdapter
    }
}