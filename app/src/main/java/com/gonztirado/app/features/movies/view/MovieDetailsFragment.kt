package com.gonztirado.app.features.movies.view

import android.os.Bundle
import android.view.View
import com.gonztirado.app.R
import com.gonztirado.app.core.view.BaseFragment
import com.gonztirado.app.core.view.close
import com.gonztirado.app.core.view.viewModel
import com.gonztirado.app.features.movies.view.MovieFailure.NonExistentMovie
import com.gonztirado.app.features.movies.viewmodel.MovieDetailsView
import com.gonztirado.app.features.movies.viewmodel.MovieDetailsViewModel
import com.gonztirado.app.features.movies.viewmodel.MovieView
import com.gonztirado.app.util.exception.Failure
import com.gonztirado.app.util.exception.Failure.NetworkConnection
import com.gonztirado.app.util.exception.Failure.ServerError
import com.gonztirado.app.util.extension.failure
import com.gonztirado.app.util.extension.loadFromUrl
import com.gonztirado.app.util.extension.loadUrlAndPostponeEnterTransition
import com.gonztirado.app.util.extension.observe
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class MovieDetailsFragment : BaseFragment() {

    companion object {
        private const val PARAM_MOVIE = "param_movie"

        fun forMovie(movie: MovieView): MovieDetailsFragment {
            val movieDetailsFragment = MovieDetailsFragment()
            val arguments = Bundle()
            arguments.putParcelable(PARAM_MOVIE, movie)
            movieDetailsFragment.arguments = arguments

            return movieDetailsFragment
        }
    }

    @Inject
    lateinit var movieDetailsAnimator: MovieDetailsAnimator

    private lateinit var movieDetailsViewModel: MovieDetailsViewModel

    override fun layoutId() = R.layout.fragment_movie_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        activity?.let { movieDetailsAnimator.postponeEnterTransition(it) }

        movieDetailsViewModel = viewModel(viewModelFactory) {
            observe(movieDetails, ::renderMovieDetails)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (firstTimeCreated(savedInstanceState)) {
            movieDetailsViewModel.loadMovieDetails((arguments?.get(PARAM_MOVIE) as MovieView).id)
        } else {
            movieDetailsAnimator.cancelTransition(moviePoster)
            moviePoster.loadFromUrl((arguments!![PARAM_MOVIE] as MovieView).poster, R.drawable.movie_empty)
        }
    }

    override fun onBackPressed() {
        movieDetailsAnimator.fadeInvisible(scrollView, movieDetails)
        movieDetailsAnimator.cancelTransition(moviePoster)
    }

    private fun renderMovieDetails(movie: MovieDetailsView?) {
        movie?.let {
            with(movie) {
                activity?.let {
                    moviePoster.loadUrlAndPostponeEnterTransition(poster, it, R.drawable.movie_empty)
                    it.toolbar.title = title
                }

                movieDirector.text = director
                movieRuntime.text = timeDuration
            }
        }
        movieDetailsAnimator.fadeVisible(scrollView, movieDetails)
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is NetworkConnection -> {
                notify(R.string.failure_network_connection); close()
            }
            is ServerError -> {
                notify(R.string.failure_server_error); close()
            }
            is NonExistentMovie -> {
                notify(R.string.failure_movie_non_existent); close()
            }
        }
    }
}
