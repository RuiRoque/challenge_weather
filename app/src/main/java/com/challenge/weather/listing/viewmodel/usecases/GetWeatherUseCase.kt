package com.challenge.weather.listing.viewmodel.usecases

import com.challenge.weather.listing.model.WeatherModel
import com.challenge.weather.listing.model.repository.retrofit.responses.WeatherResponse
import com.challenge.weather.listing.viewmodel.entities.WeatherValues
import com.challenge.weather.mvvm.viewmodel.UseCaseContract
import io.reactivex.Observable
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class GetWeatherUseCase @Inject constructor(private val weatherModel: WeatherModel) : UseCaseContract {

    private val simpleDateFormat: SimpleDateFormat by lazy {
        val locale = Locale("pt", "PT")
        val simpleDateFormat = SimpleDateFormat("HH:mm:ss", locale)
        simpleDateFormat.timeZone = TimeZone.getTimeZone("GMT")
        simpleDateFormat
    }

    fun execute(query: String): Observable<WeatherValues> {
        return weatherModel.getWeather(query)
            .map { weatherResponse -> weatherResponseToWeatherValues(weatherResponse, query) }
    }

    private fun weatherResponseToWeatherValues(weatherResponse: WeatherResponse, query: String): WeatherValues {
        return when {
            weatherResponse.isValid() -> WeatherValues(
                city = removeCountryCode(query),
                temp = weatherResponse.main.temp.toString() + "°",
                sunrise = unixUtcToHours(weatherResponse.sys.sunrise),
                sunset = unixUtcToHours(weatherResponse.sys.sunset)
            )
            weatherResponse.notFound() -> WeatherValues(valid = false, notFound = true)
            !weatherResponse.isValid() -> WeatherValues(valid = false, notFound = false)
            else -> WeatherValues(valid = false, notFound = true)
        }
    }

    private fun removeCountryCode(query: String) = query.substring(0, query.indexOf(','))

    private fun unixUtcToHours(unixSeconds: Int): String {
        val date = unixSecondsToDate(unixSeconds)
        return simpleDateFormat.format(date)
    }

    private fun unixSecondsToDate(unixSeconds: Int) = Date(unixSeconds * 1000L)
}
