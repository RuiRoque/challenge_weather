package com.challenge.weather.listing.model.repository.retrofit.responses

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.challenge.weather.mvvm.model.repository.datasources.retrofit.ServiceResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonIgnoreProperties(ignoreUnknown = true)
class WeatherResponse(
    @JsonProperty("throwable")
    var exception: Throwable? = null,

    @JsonProperty("main")
    var main: Main = Main(),

    @JsonProperty("sys")
    var sys: Sys = Sys()

) : ServiceResponse(exception), Parcelable

@Parcelize
@JsonIgnoreProperties(ignoreUnknown = true)
class Main(
    @JsonProperty("temp")
    var temp: Int = 0
) : Parcelable

@Parcelize
@JsonIgnoreProperties(ignoreUnknown = true)
class Sys(
    @JsonProperty("sunrise")
    var sunrise: Int = 0,

    @JsonProperty("sunset")
    var sunset: Int = 0

) : Parcelable
