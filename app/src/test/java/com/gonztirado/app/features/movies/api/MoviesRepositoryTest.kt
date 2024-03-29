package com.gonztirado.app.features.movies.api

import com.gonztirado.app.UnitTest
import com.gonztirado.app.features.movies.api.MoviesRepository
import com.gonztirado.app.features.movies.api.MoviesRepository.Network
import com.gonztirado.app.features.movies.api.MoviesService
import com.gonztirado.app.features.movies.api.entities.MovieDetailsEntity
import com.gonztirado.app.features.movies.api.entities.MovieSearchEntity
import com.gonztirado.app.features.movies.model.Movie
import com.gonztirado.app.features.movies.model.MovieDetails
import com.gonztirado.app.util.exception.Failure.NetworkConnection
import com.gonztirado.app.util.exception.Failure.ServerError
import com.gonztirado.app.util.extension.empty
import com.gonztirado.app.util.functional.Either
import com.gonztirado.app.util.functional.Either.Right
import com.gonztirado.app.util.platform.NetworkHandler
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import retrofit2.Call
import retrofit2.Response

class MoviesRepositoryTest : UnitTest() {

    private val MOVIE_SEARCH = "blade runner"
    private val MOVIE_ID = "tt0083658"

    private lateinit var networkRepository: MoviesRepository.Network

    @Mock private lateinit var networkHandler: NetworkHandler
    @Mock private lateinit var service: MoviesService

    @Mock private lateinit var moviesCall: Call<MovieSearchEntity>
    @Mock private lateinit var moviesResponse: Response<MovieSearchEntity>
    @Mock private lateinit var movieDetailsCall: Call<MovieDetailsEntity>
    @Mock private lateinit var movieDetailsResponse: Response<MovieDetailsEntity>

    @Before fun setUp() {
        networkRepository = Network(networkHandler, service)
    }

    @Test fun `should return empty list by default`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { moviesResponse.body() }.willReturn(null)
        given { moviesResponse.isSuccessful }.willReturn(true)
        given { moviesCall.execute() }.willReturn(moviesResponse)
        given { service.movies("star") }.willReturn(moviesCall)

        val movies = networkRepository.movies(MOVIE_SEARCH)

        movies shouldEqual Right(emptyList<Movie>())
        verify(service).movies(MOVIE_SEARCH)
    }

    @Test fun `should get movie list from service`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { moviesResponse.body() }.willReturn(MovieSearchEntity.empty())
        given { moviesResponse.isSuccessful }.willReturn(true)
        given { moviesCall.execute() }.willReturn(moviesResponse)
        given { service.movies(MOVIE_SEARCH) }.willReturn(moviesCall)

        val movies = networkRepository.movies(MOVIE_SEARCH)

        movies shouldEqual Right(MovieSearchEntity.empty())
        verify(service).movies(MOVIE_SEARCH)
    }

    @Test fun `movies service should return network failure when no connection`() {
        given { networkHandler.isConnected }.willReturn(false)

        val movies = networkRepository.movies(MOVIE_SEARCH)

        movies shouldBeInstanceOf Either::class.java
        movies.isLeft shouldEqual true
        movies.either({ failure -> failure shouldBeInstanceOf NetworkConnection::class.java }, {})
        verifyZeroInteractions(service)
    }

    @Test fun `movies service should return network failure when undefined connection`() {
        given { networkHandler.isConnected }.willReturn(null)

        val movies = networkRepository.movies(MOVIE_SEARCH)

        movies shouldBeInstanceOf Either::class.java
        movies.isLeft shouldEqual true
        movies.either({ failure -> failure shouldBeInstanceOf NetworkConnection::class.java }, {})
        verifyZeroInteractions(service)
    }

    @Test fun `movies service should return server error if no successful response`() {
        given { networkHandler.isConnected }.willReturn(true)

        val movies = networkRepository.movies(MOVIE_SEARCH)

        movies shouldBeInstanceOf Either::class.java
        movies.isLeft shouldEqual true
        movies.either({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }

    @Test fun `movies request should catch exceptions`() {
        given { networkHandler.isConnected }.willReturn(true)

        val movies = networkRepository.movies(MOVIE_SEARCH)

        movies shouldBeInstanceOf Either::class.java
        movies.isLeft shouldEqual true
        movies.either({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }

    @Test fun `should return empty movie details by default`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { movieDetailsResponse.body() }.willReturn(null)
        given { movieDetailsResponse.isSuccessful }.willReturn(true)
        given { movieDetailsCall.execute() }.willReturn(movieDetailsResponse)
        given { service.movieDetails(MOVIE_ID) }.willReturn(movieDetailsCall)

        val movieDetails = networkRepository.movieDetails(MOVIE_ID)

        movieDetails shouldEqual Right(MovieDetails.empty())
        verify(service).movieDetails(MOVIE_ID)
    }

    @Test fun `should get movie details from service`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { movieDetailsResponse.body() }.willReturn(
            MovieDetailsEntity(MOVIE_ID, "title", String.empty(), String.empty(),
                String.empty(), String.empty(), 0, String.empty(), emptyList(), true, String.empty()))
        given { movieDetailsResponse.isSuccessful }.willReturn(true)
        given { movieDetailsCall.execute() }.willReturn(movieDetailsResponse)
        given { service.movieDetails(MOVIE_ID) }.willReturn(movieDetailsCall)

        val movieDetails = networkRepository.movieDetails(MOVIE_ID)

        movieDetails shouldEqual Right(MovieDetails(MOVIE_ID, "title", String.empty(), String.empty(),
            String.empty(), String.empty(), 0, String.empty(), emptyList()))
        verify(service).movieDetails(MOVIE_ID)
    }

    @Test fun `movie details service should return network failure when no connection`() {
        given { networkHandler.isConnected }.willReturn(false)

        val movieDetails = networkRepository.movieDetails(MOVIE_ID)

        movieDetails shouldBeInstanceOf Either::class.java
        movieDetails.isLeft shouldEqual true
        movieDetails.either({ failure -> failure shouldBeInstanceOf NetworkConnection::class.java }, {})
        verifyZeroInteractions(service)
    }

    @Test fun `movie details service should return network failure when undefined connection`() {
        given { networkHandler.isConnected }.willReturn(null)

        val movieDetails = networkRepository.movieDetails(MOVIE_ID)

        movieDetails shouldBeInstanceOf Either::class.java
        movieDetails.isLeft shouldEqual true
        movieDetails.either({ failure -> failure shouldBeInstanceOf NetworkConnection::class.java }, {})
        verifyZeroInteractions(service)
    }

    @Test fun `movie details service should return server error if no successful response`() {
        given { networkHandler.isConnected }.willReturn(true)

        val movieDetails = networkRepository.movieDetails(MOVIE_ID)

        movieDetails shouldBeInstanceOf Either::class.java
        movieDetails.isLeft shouldEqual true
        movieDetails.either({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }

    @Test fun `movie details request should catch exceptions`() {
        given { networkHandler.isConnected }.willReturn(true)

        val movieDetails = networkRepository.movieDetails(MOVIE_ID)

        movieDetails shouldBeInstanceOf Either::class.java
        movieDetails.isLeft shouldEqual true
        movieDetails.either({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }
}