package com.gonztirado.app.features.movies.api.usecases

import com.gonztirado.app.features.movies.api.MoviesRepository
import com.gonztirado.app.features.movies.api.usecases.GetMovieDetails.Params
import com.gonztirado.app.features.movies.model.MovieDetails
import com.gonztirado.app.util.interactor.UseCase
import javax.inject.Inject

class GetMovieDetails
@Inject constructor(private val moviesRepository: MoviesRepository) : UseCase<MovieDetails, Params>() {

    override suspend fun run(params: Params) = moviesRepository.movieDetails(params.id)

    data class Params(val id: String)
}
