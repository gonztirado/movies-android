package com.gonztirado.app.features.movies.api

import com.gonztirado.app.UnitTest
import com.gonztirado.app.features.movies.api.MoviesRepository
import com.gonztirado.app.features.movies.api.usecases.GetMovieDetails
import com.gonztirado.app.features.movies.model.MovieDetails
import com.gonztirado.app.util.functional.Either
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class GetMovieDetailsTest : UnitTest() {

    private val MOVIE_ID = "tt0083658"

    private lateinit var getMovieDetails: GetMovieDetails

    @Mock private lateinit var moviesRepository: MoviesRepository

    @Before fun setUp() {
        getMovieDetails = GetMovieDetails(moviesRepository)
        given { moviesRepository.movieDetails(MOVIE_ID) }.willReturn(Either.Right(MovieDetails.empty()))
    }

    @Test fun `should get data from repository`() {
        runBlocking { getMovieDetails.run(GetMovieDetails.Params(MOVIE_ID)) }

        verify(moviesRepository).movieDetails(MOVIE_ID)
        verifyNoMoreInteractions(moviesRepository)
    }
}
