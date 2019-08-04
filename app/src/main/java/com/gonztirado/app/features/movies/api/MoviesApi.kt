package com.gonztirado.app.features.movies.api

import com.gonztirado.app.features.movies.api.entities.MovieDetailsEntity
import com.gonztirado.app.features.movies.api.entities.MovieEntity
import com.gonztirado.app.features.movies.api.entities.MovieSearchEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

internal interface MoviesApi {
    companion object {
        private const val PARAM_API_SEARCH_TITLE = "s"

        private const val PARAM_API_PAGE = "page"
        private const val VALUE_API_PAGE_FIRST = 1

        private const val PARAM_API_ID = "i"
    }

    @GET("?apikey=816be9e5&type=movie")
    fun movies(
        @Query(PARAM_API_SEARCH_TITLE) searchTitle: String,
        @Query(PARAM_API_PAGE) page: Int = VALUE_API_PAGE_FIRST
    ): Call<MovieSearchEntity>

    @GET("?apikey=816be9e5&type=movie")
    fun movieDetails(
        @Query(PARAM_API_ID) imdbId: String
    ): Call<MovieDetailsEntity>
}

