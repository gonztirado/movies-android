package com.gonztirado.app.features.movies.api

import com.gonztirado.app.features.movies.api.entities.MovieDetailsEntity
import com.gonztirado.app.features.movies.api.entities.MovieEntity
import com.gonztirado.app.features.movies.api.entities.MovieSearchEntity
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesService
@Inject constructor(retrofit: Retrofit) : MoviesApi {

    private val moviesApi by lazy { retrofit.create(MoviesApi::class.java) }

    override fun movies(searchTitle: String, page: Int): Call<MovieSearchEntity> {
        return moviesApi.movies(searchTitle, page)
    }

    override fun movieDetails(imdbId: String): Call<MovieDetailsEntity> {
        return moviesApi.movieDetails(imdbId)
    }
}
