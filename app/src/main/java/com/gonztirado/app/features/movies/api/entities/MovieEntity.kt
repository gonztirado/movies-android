package com.gonztirado.app.features.movies.api.entities

import com.gonztirado.app.features.movies.model.Movie

data class MovieEntity(
    private val imdbID: String,
    private val Poster: String,
    private val Title: String,
    private var Year: Int
) {

    fun toMovie() = Movie(imdbID, Poster, Title, Year)
}
