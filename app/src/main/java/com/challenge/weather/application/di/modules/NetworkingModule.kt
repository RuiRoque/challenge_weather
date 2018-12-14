package com.challenge.weather.application.di.modules

import com.challenge.weather.mvvm.model.repository.datasources.retrofit.BackendApi
import dagger.Module
import dagger.Provides
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookiePolicy
import javax.inject.Singleton

@Module class NetworkingModule {

    @Singleton
    @Provides
    fun provideBackendApi(cookieManager: CookieManager): BackendApi {
        return BackendApi(cookieManager)
    }

    @Module
    companion object {

        @JvmStatic
        @Singleton
        @Provides
        fun provideCookieManager(): CookieManager {
            val cookieManager = CookieManager()
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
            CookieHandler.setDefault(cookieManager)
            return cookieManager
        }
    }
}