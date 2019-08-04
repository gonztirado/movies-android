package com.gonztirado.app.features.movies.view

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.widget.StaggeredGridLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.gonztirado.app.R
import com.gonztirado.app.core.navigation.Navigator
import com.gonztirado.app.core.view.BaseFragment
import com.gonztirado.app.core.view.viewModel
import com.gonztirado.app.features.movies.view.MovieFailure.ListNotAvailable
import com.gonztirado.app.features.movies.viewmodel.MoviesViewModel
import com.gonztirado.app.util.exception.Failure
import com.gonztirado.app.util.exception.Failure.NetworkConnection
import com.gonztirado.app.util.exception.Failure.ServerError
import com.gonztirado.app.util.extension.failure
import com.gonztirado.app.util.extension.invisible
import com.gonztirado.app.util.extension.observe
import com.gonztirado.app.util.extension.visible
import kotlinx.android.synthetic.main.fragment_movies.*
import javax.inject.Inject

class MoviesFragment : BaseFragment(), TextWatcher {
    @Inject lateinit var navigator: Navigator
    @Inject lateinit var moviesAdapter: MoviesAdapter

    private lateinit var moviesViewModel: MoviesViewModel

    override fun layoutId() = R.layout.fragment_movies

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        moviesViewModel = viewModel(viewModelFactory) {
            observe(movies, ::renderMoviesList)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        loadMoviesList()
    }


    private fun initializeView() {
        movieList.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        movieList.adapter = moviesAdapter
        // TODO add poster click listener
        movieSearch.addTextChangedListener(this)
    }

    override fun afterTextChanged(s: Editable?) {loadMoviesList()}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    private fun loadMoviesList() {
        if (movieSearch.text.length < 3 ) {
            movieList.invisible()
            emptyView.visible()
            return
        }

        emptyView.invisible()
        movieList.visible()
        showProgress()
        moviesViewModel.loadMovies(movieSearch.text.toString())
    }

    private fun renderMoviesList(movies: List<MovieView>?) {
        moviesAdapter.collection = movies.orEmpty()
        hideProgress()
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is NetworkConnection -> renderFailure(R.string.failure_network_connection)
            is ServerError -> renderFailure(R.string.failure_server_error)
            is ListNotAvailable -> renderFailure(R.string.failure_movies_list_unavailable)
        }
    }

    private fun renderFailure(@StringRes message: Int) {
        movieList.invisible()
        emptyView.visible()
        hideProgress()
        notifyWithAction(message, R.string.action_refresh, ::loadMoviesList)
    }
}
