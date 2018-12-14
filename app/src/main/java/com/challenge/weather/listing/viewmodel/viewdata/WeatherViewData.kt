package com.challenge.weather.listing.viewmodel.viewdata

import com.challenge.weather.listing.viewmodel.entities.WeatherValues

data class WeatherViewData(
    var event: Event,
    var weather: WeatherValues = WeatherValues()) {

    enum class Event {
        ShowLoading,
        ShowError,
        ShowNotFound,
        ShowResults
    }
}
