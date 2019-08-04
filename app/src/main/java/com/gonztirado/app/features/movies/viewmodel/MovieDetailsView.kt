package com.gonztirado.app.features.movies.viewmodel

data class MovieDetailsView(
    val id: String,
    val title: String,
    val poster: String,
    val director: String,
    val timeDuration: String,
    val ratings: List<MovieRatingView>
)
