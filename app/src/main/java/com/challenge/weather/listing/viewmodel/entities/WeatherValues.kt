package com.challenge.weather.listing.viewmodel.entities

data class WeatherValues(
    var city: String = "",
    var temp: String = "",
    var sunrise: String = "",
    var sunset: String = "",
    val valid: Boolean = true,
    val notFound: Boolean = false
)
