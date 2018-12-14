package com.challenge.weather.listing.model

import com.challenge.weather.listing.model.repository.retrofit.WeatherNetworkFacade
import com.challenge.weather.listing.model.repository.retrofit.responses.WeatherResponse
import com.challenge.weather.listing.model.repository.sharedpreferences.SharedPreferencesLastQuery
import com.challenge.weather.mvvm.model.ModelContract
import com.challenge.weather.mvvm.model.repository.RepositoryManager
import io.reactivex.Observable
import javax.inject.Inject

class WeatherModel @Inject constructor(override val repositoryManager: RepositoryManager) : ModelContract {

    fun getWeather(query: String): Observable<WeatherResponse> {
        return WeatherNetworkFacade.getWeather(repositoryManager, query)
    }

    var lastQuery: String
        get() = SharedPreferencesLastQuery(repositoryManager).lastQuery
        set(value) {
            val sharedPreferencesLastQuery = SharedPreferencesLastQuery(repositoryManager)
            sharedPreferencesLastQuery.lastQuery = value
        }
}
