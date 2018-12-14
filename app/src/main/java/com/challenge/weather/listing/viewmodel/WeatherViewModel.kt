package com.challenge.weather.listing.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.challenge.weather.listing.viewmodel.controlflow.WeatherControlFlow
import com.challenge.weather.listing.viewmodel.usecases.GetLastQueryUseCase
import com.challenge.weather.listing.viewmodel.usecases.GetWeatherUseCase
import com.challenge.weather.listing.viewmodel.usecases.SetLastQueryUseCase
import com.challenge.weather.listing.viewmodel.viewdata.WeatherViewData
import com.challenge.weather.mvvm.viewmodel.MvvmViewModel
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    application: Application,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getLastQueryUseCase: GetLastQueryUseCase,
    private val setLastQueryUseCase: SetLastQueryUseCase
) : MvvmViewModel(application) {

    val liveData: MutableLiveData<WeatherViewData> = MutableLiveData()

    var lastQuery: String
        get() = getLastQueryUseCase.execute()
        set(value) = setLastQueryUseCase.execute(value)

    init {
        liveData.value = WeatherViewData(
            event = WeatherViewData.Event.ShowLoading
        )
    }

    fun getListing(query: String) {
        getWeatherUseCase.execute(query)
            .compose(applySchedulers())
            .doOnComplete { lastQuery = query }
            .subscribe { weatherValues -> WeatherControlFlow.notifyView(liveData, weatherValues) }
            .addTo(compositeDisposable)
    }
}
