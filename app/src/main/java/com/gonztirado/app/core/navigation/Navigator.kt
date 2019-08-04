package com.gonztirado.app.core.navigation

import android.content.Context
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.FragmentActivity
import android.view.View
import android.widget.ImageView
import com.gonztirado.app.features.movies.view.MovieDetailsActivity
import com.gonztirado.app.features.movies.view.MoviesActivity
import com.gonztirado.app.features.movies.viewmodel.MovieView
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator
@Inject constructor() {

    fun showMain(context: Context) {
        showMovies(context)
    }

    private fun showMovies(context: Context) = context.startActivity(MoviesActivity.callingIntent(context))

    fun showMovieDetails(activity: FragmentActivity, movie: MovieView, navigationExtras: Extras) {
        val intent = MovieDetailsActivity.callingIntent(activity, movie)
        val sharedView = navigationExtras.transitionSharedElement as ImageView
        val activityOptions = ActivityOptionsCompat
            .makeSceneTransitionAnimation(activity, sharedView, sharedView.transitionName)
        activity.startActivity(intent, activityOptions.toBundle())
    }

    class Extras(val transitionSharedElement: View)

}



