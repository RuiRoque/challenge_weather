package com.challenge.weather.application

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.app.Service
import android.content.BroadcastReceiver
import com.challenge.weather.application.di.components.AppComponent
import com.challenge.weather.application.di.components.DaggerAppComponent
import com.challenge.weather.application.di.modules.AppModule
import dagger.android.*
import javax.inject.Inject

@SuppressLint("Registered")
open class InjectApplication : Application(), HasActivityInjector, HasServiceInjector, HasBroadcastReceiverInjector {

    lateinit var appComponent: AppComponent
        private set

    companion object {
        private lateinit var INSTANCE: InjectApplication
        @JvmStatic
        fun get(): InjectApplication = INSTANCE
    }

    @Inject lateinit var dispatchingActivityAndroidInjector: DispatchingAndroidInjector<Activity>
    @Inject lateinit var dispatchingServiceAndroidInjector: DispatchingAndroidInjector<Service>
    @Inject lateinit var dispatchingBroadcastReceiverAndroidInjector: DispatchingAndroidInjector<BroadcastReceiver>

    override fun serviceInjector(): AndroidInjector<Service> = dispatchingServiceAndroidInjector
    override fun activityInjector(): DispatchingAndroidInjector<Activity> = dispatchingActivityAndroidInjector
    override fun broadcastReceiverInjector(): AndroidInjector<BroadcastReceiver> = dispatchingBroadcastReceiverAndroidInjector

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        initializeDaggerInjection()
    }

    private fun initializeDaggerInjection() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()

        appComponent.inject(this as PlaygroundApplication)
    }
}
