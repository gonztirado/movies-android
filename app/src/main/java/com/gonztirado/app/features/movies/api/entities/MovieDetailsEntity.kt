package com.gonztirado.app.features.movies.api.entities

import com.gonztirado.app.features.movies.model.MovieDetails
import com.gonztirado.app.util.extension.empty

data class MovieDetailsEntity(
    private val imdbID: String,
    private val Title: String,
    private val Poster: String,
    private val Plot: String,
    private val Actors: String,
    private val Director: String,
    private val Year: Int,
    private val Awards: String,
    private val Ratings: List<MovieRatingEntity>,
    val Response: Boolean,
    val Error: String
) {

    companion object {
        fun empty() = MovieDetailsEntity(
            String.empty(), String.empty(), String.empty(), String.empty(),
            String.empty(), String.empty(), 0, String.empty(), emptyList(), true, Error = String.empty()
        )
    }

    fun toMovieDetails() =
        MovieDetails(imdbID, Title, Poster, Plot, Actors, Director, Year, Awards, Ratings.map { it.toMovieRating() })
}
