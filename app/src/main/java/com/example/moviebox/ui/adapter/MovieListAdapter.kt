package com.example.moviebox.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat

import androidx.recyclerview.widget.RecyclerView
import com.example.moviebox.R
import com.example.moviebox.data.Movie

class MovieListAdapter(
    private val movieList: ArrayList<Movie>,
    private var onMovieClick: ((Movie) -> Unit)? = null
): RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {

    private lateinit var starButtons: Array<Button>

    class MovieListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewMovieImage)
        val nameTextView: TextView = itemView.findViewById(R.id.movieName)
        val directorTextView: TextView = itemView.findViewById(R.id.movieDirector)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent, false)
        return MovieListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val movie = movieList[position]

        holder.itemView.setOnClickListener {
            onMovieClick?.invoke(movie)
        }

        holder.imageView.setImageResource(movie.imageUrl)
        holder.nameTextView.text = movie.name
        holder.directorTextView.text = movie.director

        starButtons = arrayOf(
            holder.itemView.findViewById(R.id.starOne),
            holder.itemView.findViewById(R.id.starTwo),
            holder.itemView.findViewById(R.id.starThree),
            holder.itemView.findViewById(R.id.starFour),
            holder.itemView.findViewById(R.id.starFive),
        )

        updateStarImages(holder.itemView, movie)
    }

    private fun updateStarImages(itemView: View, movie: Movie) {
        val rating = if(movie.rating !== null) movie.rating else 0

        // Fill stars until rating
        for (i in 0 until rating!!) {
            starButtons[i].background = ContextCompat.getDrawable(itemView.context, R.drawable.ic_star_24)
        }

        // Clear other stars
        for (i in rating  until 5) {
            starButtons[i].background = ContextCompat.getDrawable(itemView.context, R.drawable.ic_star_outline_24)
        }
    }
}