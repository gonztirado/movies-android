package com.gonztirado.app.core.di

import com.gonztirado.app.AndroidApplication
import com.gonztirado.app.core.di.viewmodel.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(application: AndroidApplication)

    // TODO add here components injection
}
