package com.gonztirado.app.features.movies.view

import android.os.Bundle
import android.os.Handler
import android.support.annotation.StringRes
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.gonztirado.app.R
import com.gonztirado.app.core.navigation.Navigator
import com.gonztirado.app.core.view.BaseFragment
import com.gonztirado.app.core.view.viewModel
import com.gonztirado.app.features.movies.view.MovieFailure.ListNotAvailable
import com.gonztirado.app.features.movies.viewmodel.MovieView
import com.gonztirado.app.features.movies.viewmodel.MoviesViewModel
import com.gonztirado.app.util.exception.Failure
import com.gonztirado.app.util.exception.Failure.NetworkConnection
import com.gonztirado.app.util.exception.Failure.ServerError
import com.gonztirado.app.util.extension.failure
import com.gonztirado.app.util.extension.invisible
import com.gonztirado.app.util.extension.observe
import com.gonztirado.app.util.extension.visible
import com.jakewharton.rxbinding.widget.RxTextView
import kotlinx.android.synthetic.main.fragment_movies.*
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MoviesFragment : BaseFragment() {

    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var moviesAdapter: MoviesAdapter

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
        Handler().postDelayed({ hideKeyboard(movieSearch) }, 200)
    }


    private fun initializeView() {
        movieList.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        movieList.adapter = moviesAdapter
        moviesAdapter.clickListener = { movie, navigationExtras ->
            navigator.showMovieDetails(activity!!, movie, navigationExtras)
        }

        RxTextView.textChanges(movieSearch)
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Action1 {
                loadMoviesList()
            })
    }

    private fun loadMoviesList() {
        if (movieSearch.text.length < 3) {
            showEmptyView(R.string.movies_search_empty_min_characters)
            return
        }

        hideEmptyView()
        showProgress()
        moviesViewModel.loadMovies(movieSearch.text.toString())
    }


    private fun renderMoviesList(movies: List<MovieView>?) {
        moviesAdapter.collection = movies.orEmpty()
        hideProgress()
        hideLastNotification()

        if (movies == null || movies.isEmpty()) {
            showEmptyView(R.string.movies_search_empty_not_movie_found)
        }
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is NetworkConnection -> renderFailure(R.string.failure_network_connection)
            is ServerError -> renderFailure(R.string.failure_server_error)
            is ListNotAvailable -> renderFailure(R.string.failure_movies_list_unavailable)
        }
    }

    private fun renderFailure(@StringRes message: Int) {
        showEmptyView(R.string.movies_search_empty_not_movie_found)
        hideProgress()
        notifyWithAction(message, R.string.action_refresh, ::loadMoviesList)
    }

    private fun hideEmptyView() {
        emptyView.invisible()
        movieList.visible()
    }

    private fun showEmptyView(@StringRes resId: Int) {
        movieList.invisible()
        emptyView.visible()
        emptyViewText.text = getString(resId)
    }
}
