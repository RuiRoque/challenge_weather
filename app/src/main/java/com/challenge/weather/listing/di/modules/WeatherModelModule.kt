package com.challenge.weather.listing.di.modules

import com.challenge.weather.application.di.mapkeys.ModelKey
import com.challenge.weather.application.di.scopes.AppScope
import com.challenge.weather.listing.model.WeatherModel
import com.challenge.weather.mvvm.model.ModelContract
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class WeatherModelModule {

    @AppScope
    @Binds
    @IntoMap
    @ModelKey(WeatherModel::class)
    abstract fun bindWeatherModel(weatherModel: WeatherModel): ModelContract
}
