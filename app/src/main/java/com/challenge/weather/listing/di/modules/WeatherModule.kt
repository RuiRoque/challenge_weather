package com.challenge.weather.listing.di.modules

import com.challenge.weather.application.di.scopes.AppScope
import com.challenge.weather.listing.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module abstract class WeatherModule {

    @AppScope
    @ContributesAndroidInjector(
            modules = [
                WeatherViewModelFactoryModule::class,
                WeatherModelModule::class,
                WeatherViewModelModule::class,
                WeatherUseCaseModule::class
            ]
    )
    abstract fun bindMainActivity(): MainActivity
}
