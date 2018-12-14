package com.challenge.weather.listing.di.modules

import android.arch.lifecycle.ViewModel
import com.challenge.weather.application.di.mapkeys.ViewModelKey
import com.challenge.weather.application.di.scopes.AppScope
import com.challenge.weather.listing.viewmodel.WeatherViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class WeatherViewModelModule {

    @AppScope
    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel::class)
    abstract fun bindWeatherViewModel(weatherViewModel: WeatherViewModel): ViewModel
}
