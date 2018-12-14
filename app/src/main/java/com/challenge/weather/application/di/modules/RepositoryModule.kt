package com.challenge.weather.application.di.modules

import android.content.Context
import com.challenge.weather.mvvm.model.repository.RepositoryCache
import com.challenge.weather.mvvm.model.repository.datasources.retrofit.BackendApi
import com.challenge.weather.mvvm.model.repository.datasources.retrofit.RepositoryRetrofit
import com.challenge.weather.mvvm.model.repository.datasources.sharedpreferences.RepositorySharedPreferences
import dagger.Module
import dagger.Provides

@Module class RepositoryModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        fun provideRepositoryRetrofit(repositoryCache: RepositoryCache, backendApi: BackendApi): RepositoryRetrofit {
            return RepositoryRetrofit(repositoryCache, backendApi)
        }

        @JvmStatic
        @Provides
        fun provideRepositoryCache(): RepositoryCache {
            return RepositoryCache()
        }

        @JvmStatic
        @Provides
        fun provideRepositorySharedPreferences(context: Context, repositoryCache: RepositoryCache): RepositorySharedPreferences {
            return RepositorySharedPreferences(context, repositoryCache)
        }
    }
}
