package com.gonztirado.app.features.movies.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.gonztirado.app.core.view.BaseViewModel
import com.gonztirado.app.features.movies.api.usecases.GetMovieDetails
import com.gonztirado.app.features.movies.api.usecases.GetMovieDetails.Params
import com.gonztirado.app.features.movies.model.MovieDetails
import javax.inject.Inject

class MovieDetailsViewModel
@Inject constructor(private val getMovieDetails: GetMovieDetails) : BaseViewModel() {

    var movieDetails: MutableLiveData<MovieDetailsView> = MutableLiveData()

    fun loadMovieDetails(movieId: String) =
        getMovieDetails(Params(movieId)) { it.either(::handleFailure, ::handleMovieDetails) }

    private fun handleMovieDetails(movie: MovieDetails) {
        this.movieDetails.value = MovieDetailsView(
            movie.id, movie.title, movie.poster,
            movie.director, movie.timeDuration, movie.ratings.map { MovieRatingView(it.source, it.value) }
        )
    }
}