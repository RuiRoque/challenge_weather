package com.challenge.weather.mvvm.model.repository

import com.challenge.weather.mvvm.model.repository.datasources.retrofit.RepositoryRetrofit
import com.challenge.weather.mvvm.model.repository.datasources.sharedpreferences.RepositorySharedPreferences
import javax.inject.Inject

open class RepositoryManager @Inject constructor(
        val retrofit: RepositoryRetrofit,
        val sharedPreferences: RepositorySharedPreferences
) : WeatherRepositoryManager
