package com.gonztirado.app.features.movies.api.entities

import com.gonztirado.app.features.movies.model.Movie
import com.gonztirado.app.util.extension.empty

data class MovieSearchEntity(
    val Search: List<MovieEntity>?,
    val totalResults: Int?,
    val Response: Boolean,
    val Error: String
) {
    companion object {
        fun empty() = MovieSearchEntity(
            emptyList(), 0, true, String.empty()
        )
    }

    fun toMovieList() : List<Movie> {
        if(Search != null && Search.isNotEmpty() ) {
            return Search.map { it.toMovie() }
        }
        return emptyList()
    }
}
