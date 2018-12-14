package com.challenge.weather.listing.model.repository.retrofit

import com.challenge.weather.listing.model.repository.retrofit.responses.WeatherResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherServices {
    @GET("data/2.5/weather?units=metric&appid=c6c61c4ef62a86be4abf775d53025b6f")
    fun getWeather(
            @Query("q") cityAndCountryCode: String
    ): Observable<WeatherResponse>
}
