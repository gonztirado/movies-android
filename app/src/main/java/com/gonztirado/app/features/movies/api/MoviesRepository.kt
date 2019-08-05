package com.gonztirado.app.features.movies.api

import com.gonztirado.app.features.movies.api.entities.MovieDetailsEntity
import com.gonztirado.app.features.movies.api.entities.MovieSearchEntity
import com.gonztirado.app.features.movies.model.Movie
import com.gonztirado.app.features.movies.model.MovieDetails
import com.gonztirado.app.util.exception.Failure
import com.gonztirado.app.util.exception.Failure.NetworkConnection
import com.gonztirado.app.util.exception.Failure.ServerError
import com.gonztirado.app.util.functional.Either
import com.gonztirado.app.util.functional.Either.Left
import com.gonztirado.app.util.functional.Either.Right
import com.gonztirado.app.util.platform.NetworkHandler
import retrofit2.Call
import javax.inject.Inject

interface MoviesRepository {
    fun movies(searchTitle: String): Either<Failure, List<Movie>>
    fun movieDetails(movieId: String): Either<Failure, MovieDetails>

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: MoviesService
    ) : MoviesRepository {

        override fun movies(searchTitle: String): Either<Failure, List<Movie>> {
            return when (networkHandler.isConnected) {
                true -> request(
                    service.movies(searchTitle), {
                        searchEntity -> searchEntity.toMovieList()
                    },
                    MovieSearchEntity.empty()
                )
                false, null -> Left(NetworkConnection)
            }
        }

        override fun movieDetails(movieId: String): Either<Failure, MovieDetails> {
            return when (networkHandler.isConnected) {
                true -> request(service.movieDetails(movieId), { it.toMovieDetails() }, MovieDetailsEntity.empty())
                false, null -> Left(NetworkConnection)
            }
        }

        private fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
            return try {
                val response = call.execute()
                when (response.isSuccessful) {
                    true -> Right(transform((response.body() ?: default)))
                    false -> Left(ServerError)
                }
            } catch (exception: Throwable) {
                Left(ServerError)
            }
        }
    }
}
