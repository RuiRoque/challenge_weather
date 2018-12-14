package com.challenge.weather.listing.viewmodel.usecases

import com.challenge.weather.listing.model.WeatherModel
import com.challenge.weather.mvvm.viewmodel.UseCaseContract
import javax.inject.Inject


class SetLastQueryUseCase @Inject constructor(private val weatherModel: WeatherModel) : UseCaseContract {

    fun execute(lastQuery: String) {
        weatherModel.lastQuery = lastQuery
    }
}
