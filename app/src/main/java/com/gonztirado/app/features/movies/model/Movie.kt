package com.gonztirado.app.features.movies.model

import com.gonztirado.app.util.extension.empty

data class Movie(val id: String, val poster: String, val title: String, var year: Int) {

    companion object {
        fun empty() = Movie(String.empty(), String.empty(), String.empty(), 0)
    }
}
