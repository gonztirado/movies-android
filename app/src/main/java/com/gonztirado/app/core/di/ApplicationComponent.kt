package com.gonztirado.app.core.di

import com.gonztirado.app.AndroidApplication
import com.gonztirado.app.core.di.viewmodel.ViewModelModule
import com.gonztirado.app.core.navigation.RouteActivity
import com.gonztirado.app.features.movies.view.MoviesFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(application: AndroidApplication)
    fun inject(routeActivity: RouteActivity)

    fun inject(moviesFragment: MoviesFragment)
}
