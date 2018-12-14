package com.challenge.weather.application.di.components

import com.challenge.weather.application.PlaygroundApplication
import com.challenge.weather.application.di.modules.AppModule
import com.challenge.weather.application.di.modules.NetworkingModule
import com.challenge.weather.application.di.modules.RepositoryModule
import com.challenge.weather.listing.di.modules.WeatherModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidSupportInjectionModule::class,
            AppModule::class,
            NetworkingModule::class,
            RepositoryModule::class,
            WeatherModule::class
        ]
)
interface AppComponent {
    fun inject(application: PlaygroundApplication)
}
