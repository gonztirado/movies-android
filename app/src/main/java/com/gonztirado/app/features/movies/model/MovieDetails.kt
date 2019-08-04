package com.gonztirado.app.features.movies.model

import com.gonztirado.app.util.extension.empty

data class MovieDetails(val id: String,
                        val title: String,
                        val poster: String,
                        val summary: String,
                        val actors: String,
                        val director: String,
                        val year: Int,
                        val awards: String,
                        val ratings: List<MovieRating>) {

    // TODO include ratings

    companion object {
        fun empty() = MovieDetails(
            String.empty(), String.empty(), String.empty(), String.empty(),
            String.empty(), String.empty(), 0, String.empty(), emptyList()
        )
    }
}


