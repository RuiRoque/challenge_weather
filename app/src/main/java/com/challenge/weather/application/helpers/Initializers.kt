package com.challenge.weather.application.helpers

import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import com.challenge.weather.application.PlaygroundApplication
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.Protocol
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookiePolicy

object Initializers {

    fun initializeLog(application: PlaygroundApplication) {
        val tagPrefix = "Weather_"
        Log.setTagPrefix(tagPrefix)
        Log.configFromContext(application)
    }

    fun initializePicasso(application: PlaygroundApplication) {
        val cookieManager = CookieManager()
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
        CookieHandler.setDefault(cookieManager)

        val okHttpClient = OkHttpClient.Builder()
                .protocols(listOf(Protocol.HTTP_1_1))
                .cookieJar(JavaNetCookieJar(cookieManager))
                .build()

        Picasso.setSingletonInstance(Picasso.Builder(application.applicationContext)
                .downloader(OkHttp3Downloader(okHttpClient))
                .build())
    }
}
