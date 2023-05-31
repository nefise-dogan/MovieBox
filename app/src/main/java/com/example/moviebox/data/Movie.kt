package com.example.moviebox.data

data class Movie(
    var id: String,
    var name: String,
    var description: String,
    var imageUrl: Int,
    var director: String,
    var year: Int,
    var rating: Int?,
    var inWatchList: Boolean
)
