package com.gonztirado.app.features.movies.view

import android.content.Context
import android.content.Intent
import com.gonztirado.app.core.view.BaseActivity
import com.gonztirado.app.features.movies.viewmodel.MovieView

class MovieDetailsActivity : BaseActivity() {

    companion object {
        private const val INTENT_EXTRA_PARAM_MOVIE = "com.gonztirado.app.INTENT_PARAM_MOVIE"

        fun callingIntent(context: Context, movie: MovieView): Intent {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(INTENT_EXTRA_PARAM_MOVIE, movie)
            return intent
        }
    }

    override fun fragment() =
        MovieDetailsFragment.forMovie(intent.getParcelableExtra(INTENT_EXTRA_PARAM_MOVIE))
}
