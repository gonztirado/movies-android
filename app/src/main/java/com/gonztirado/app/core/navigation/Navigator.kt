package com.gonztirado.app.core.navigation

import android.content.Context
import com.gonztirado.app.features.MainActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator
@Inject constructor() {

    fun showMain(context: Context) {
        context.startActivity(MainActivity.callingIntent(context))
    }
}



