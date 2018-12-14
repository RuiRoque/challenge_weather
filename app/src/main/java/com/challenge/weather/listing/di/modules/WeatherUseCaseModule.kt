package com.challenge.weather.listing.di.modules

import com.challenge.weather.application.di.mapkeys.UseCaseKey
import com.challenge.weather.application.di.scopes.AppScope
import com.challenge.weather.listing.viewmodel.usecases.GetLastQueryUseCase
import com.challenge.weather.listing.viewmodel.usecases.GetWeatherUseCase
import com.challenge.weather.listing.viewmodel.usecases.SetLastQueryUseCase
import com.challenge.weather.mvvm.viewmodel.UseCaseContract
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class WeatherUseCaseModule {

    @AppScope
    @Binds
    @IntoMap
    @UseCaseKey(GetWeatherUseCase::class)
    abstract fun provideGetWeatherUseCase(getWeatherUseCase: GetWeatherUseCase): UseCaseContract

    @AppScope
    @Binds
    @IntoMap
    @UseCaseKey(GetLastQueryUseCase::class)
    abstract fun provideGetLastQueryUseCase(getWeatherUseCase: GetLastQueryUseCase): UseCaseContract

    @AppScope
    @Binds
    @IntoMap
    @UseCaseKey(GetLastQueryUseCase::class)
    abstract fun provideSetLastQueryUseCase(setLastQueryUseCase: SetLastQueryUseCase): UseCaseContract
}
