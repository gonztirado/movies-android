package com.gonztirado.app.features.movies.view

import android.content.Context
import android.content.Intent
import com.gonztirado.app.core.view.BaseActivity

class MoviesActivity : BaseActivity() {

    companion object {
        fun callingIntent(context: Context) = Intent(context, MoviesActivity::class.java)
    }

    override fun fragment() = MoviesFragment()
}
