package com.gonztirado.app.features.movies.api.usecases

import com.gonztirado.app.features.movies.api.MoviesRepository
import com.gonztirado.app.features.movies.api.usecases.GetMovies.Params
import com.gonztirado.app.features.movies.model.Movie
import com.gonztirado.app.util.interactor.UseCase
import javax.inject.Inject

class GetMovies
@Inject constructor(private val moviesRepository: MoviesRepository) : UseCase<List<Movie>, Params>() {

    override suspend fun run(params: Params) = moviesRepository.movies(params.searchTitle)

    data class Params(val searchTitle: String)
}
