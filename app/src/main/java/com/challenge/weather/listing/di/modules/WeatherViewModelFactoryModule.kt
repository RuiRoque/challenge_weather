package com.challenge.weather.listing.di.modules

import android.arch.lifecycle.ViewModelProvider
import com.challenge.weather.application.di.scopes.AppScope
import com.challenge.weather.mvvm.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module abstract class WeatherViewModelFactoryModule {

    @Binds
    @AppScope
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
