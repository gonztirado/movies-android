package com.gonztirado.app.features.movies.api.entities

import com.gonztirado.app.features.movies.model.MovieRating

data class MovieRatingEntity(
    private val Source: String,
    private val Value: String
) {

    fun toMovieRating() = MovieRating(Source, Value)
}
