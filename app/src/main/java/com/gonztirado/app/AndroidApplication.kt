package com.gonztirado.app

import android.app.Application
import com.gonztirado.sample.movies.BuildConfig
import com.squareup.leakcanary.LeakCanary

class AndroidApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        this.initializeLeakDetection()
    }

    private fun initializeLeakDetection() {
        if (BuildConfig.DEBUG) LeakCanary.install(this)
    }
}
