package com.gonztirado.app.core.navigation

import android.content.Context
import com.gonztirado.app.features.movies.view.MoviesActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator
@Inject constructor() {

    fun showMain(context: Context) {
        showMovies(context)
    }

    private fun showMovies(context: Context) = context.startActivity(MoviesActivity.callingIntent(context))


}



