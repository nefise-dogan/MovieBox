package com.example.moviebox.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviebox.data.Movie
import com.example.moviebox.data.MovieDatabase
import com.example.moviebox.databinding.FragmentWatchListBinding
import com.example.moviebox.ui.adapter.MovieGridAdapter

class WatchListFragment : Fragment() {
    private lateinit var binding: FragmentWatchListBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieList: ArrayList<Movie>
    private lateinit var movieGridAdapter: MovieGridAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWatchListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        movieList = MovieDatabase.getWatchList()

        movieGridAdapter = MovieGridAdapter(movieList) {
            val action =
                WatchListFragmentDirections.actionWatchListFragmentToMovieDescriptionFragment(
                    it.id
                )
            Navigation.findNavController(view).navigate(action)
        }

        recyclerView.adapter = movieGridAdapter
    }
}