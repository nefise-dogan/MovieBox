package com.example.moviebox.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import androidx.recyclerview.widget.RecyclerView
import com.example.moviebox.R
import com.example.moviebox.data.Movie

class MovieGridAdapter(
    private val movieList: ArrayList<Movie>,
    var onMovieClick: ((Movie) -> Unit)? = null
    ): RecyclerView.Adapter<MovieGridAdapter.MovieGridViewHolder>() {

    class MovieGridViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.imageViewMovieImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieGridViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_grid_item, parent, false)
        return MovieGridViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieGridViewHolder, position: Int) {
        val movie = movieList[position]

        holder.imageView.setImageResource(movie.imageUrl)
        holder.imageView.setOnClickListener {
            onMovieClick?.invoke(movie)
        }
    }
}