package com.challenge.weather.listing.viewmodel.controlflow

import android.arch.lifecycle.MutableLiveData
import com.challenge.weather.listing.viewmodel.entities.WeatherValues
import com.challenge.weather.listing.viewmodel.viewdata.WeatherViewData

object WeatherControlFlow {

    fun notifyView(eventLiveData: MutableLiveData<WeatherViewData>, weatherValues: WeatherValues) {
        when {
            weatherValues.valid -> notifyViewWithResults(eventLiveData, weatherValues)
            weatherValues.notFound -> notifyViewWithNotFound(eventLiveData)
            !weatherValues.valid -> notifyViewWithError(eventLiveData)
        }
    }

    private fun notifyViewWithResults(liveData: MutableLiveData<WeatherViewData>, weatherValues: WeatherValues) {
        liveData.value = WeatherViewData(
                event = WeatherViewData.Event.ShowResults,
                weather = weatherValues
        )
    }

    private fun notifyViewWithError(eventLiveData: MutableLiveData<WeatherViewData>) {
        eventLiveData.value = WeatherViewData(
                event = WeatherViewData.Event.ShowError
        )
    }

    private fun notifyViewWithNotFound(eventLiveData: MutableLiveData<WeatherViewData>) {
        eventLiveData.value = WeatherViewData(
            event = WeatherViewData.Event.ShowNotFound
        )
    }
}
