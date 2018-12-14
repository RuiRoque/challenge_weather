package com.challenge.weather.listing.viewmodel.usecases

import com.challenge.weather.listing.model.WeatherModel
import com.challenge.weather.mvvm.viewmodel.UseCaseContract
import javax.inject.Inject


class GetLastQueryUseCase @Inject constructor(private val weatherModel: WeatherModel) : UseCaseContract {

    fun execute() = weatherModel.lastQuery
}
