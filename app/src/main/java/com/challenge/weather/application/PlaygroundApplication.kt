package com.challenge.weather.application

import android.support.v7.app.AppCompatDelegate
import com.challenge.weather.application.helpers.Initializers

class PlaygroundApplication : InjectApplication() {

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        Initializers.initializeLog(this)
        Initializers.initializePicasso(this)
    }
}
