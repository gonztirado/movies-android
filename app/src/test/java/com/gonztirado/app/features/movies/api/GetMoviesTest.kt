package com.gonztirado.app.features.movies.api

import com.gonztirado.app.UnitTest
import com.gonztirado.app.features.movies.api.MoviesRepository
import com.gonztirado.app.features.movies.api.usecases.GetMovies
import com.gonztirado.app.features.movies.model.Movie
import com.gonztirado.app.util.functional.Either
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class GetMoviesTest : UnitTest() {

    private val MOVIE_SEARCH = "blade runner"

    private lateinit var getMovies: GetMovies

    @Mock private lateinit var moviesRepository: MoviesRepository

    @Before fun setUp() {
        getMovies = GetMovies(moviesRepository)
        given { moviesRepository.movies(MOVIE_SEARCH) }.willReturn(Either.Right(listOf(Movie.empty())))
    }

    @Test fun `should get data from repository`() {
        runBlocking { getMovies.run(GetMovies.Params(MOVIE_SEARCH)) }

        verify(moviesRepository).movies(MOVIE_SEARCH)
        verifyNoMoreInteractions(moviesRepository)
    }
}
