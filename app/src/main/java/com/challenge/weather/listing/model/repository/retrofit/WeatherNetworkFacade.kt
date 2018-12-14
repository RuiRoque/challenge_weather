package com.challenge.weather.listing.model.repository.retrofit

import com.challenge.weather.application.helpers.Log
import com.challenge.weather.listing.model.repository.retrofit.responses.WeatherResponse
import com.challenge.weather.mvvm.model.repository.RepositoryManager
import io.reactivex.Observable

object WeatherNetworkFacade {

    private val TAG: String = WeatherNetworkFacade::class.java.simpleName

    fun getWeather(repositoryManager: RepositoryManager, query: String): Observable<WeatherResponse> {
        return getWeatherServices(repositoryManager).getWeather(cityAndCountryCode = query)
            .onErrorReturn { throwable ->
                Log.e(TAG, "getWeather() - Unable to get weather from backend.", throwable)
                WeatherResponse(throwable)
            }
    }

    private fun getWeatherServices(repositoryManager: RepositoryManager): WeatherServices {
        return repositoryManager.retrofit.getRetrofitServices(WeatherServices::class.java)
    }
}
