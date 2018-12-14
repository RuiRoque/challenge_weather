package com.challenge.weather.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.challenge.weather.listing.model.WeatherModel
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetWeatherUseCaseTest {

    @get:Rule var instantExecutorRule = InstantTaskExecutorRule()


    @InjectMockKs
//    var getWeatherUseCase = GetWeatherUseCaseTest(getMockWeatherModel())

    private lateinit var weatherModel: WeatherModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
//        configureMockObjectRemoteConfigTooltips()
    }

    @Test
    fun whenRequestWeatherThenGetCorrectResult() {
    }
}
