package com.gonztirado.app.features.movies.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.gonztirado.app.core.view.BaseViewModel
import com.gonztirado.app.features.movies.api.usecases.GetMovies
import com.gonztirado.app.features.movies.model.Movie
import javax.inject.Inject

class MoviesViewModel
@Inject constructor(private val getMovies: GetMovies) : BaseViewModel() {

    var movies: MutableLiveData<List<MovieView>> = MutableLiveData()

    fun loadMovies(searchTitle: String) = getMovies(GetMovies.Params(searchTitle)) { it.either(::handleFailure, ::handleMovieList) }

    private fun handleMovieList(movies: List<Movie>) {
        this.movies.value = movies.map { MovieView(it.id, it.poster, it.title) }
    }
}