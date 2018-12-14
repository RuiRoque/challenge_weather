package com.challenge.weather.mvvm.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import io.reactivex.CompletableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

open class MvvmViewModel(application: Application) : AndroidViewModel(application), LifecycleObserver {

    protected var compositeDisposable = CompositeDisposable()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unDispose() {
        compositeDisposable.clear()
    }

    protected fun <T> applySchedulers(
            schedulerSubscribe: Scheduler = Schedulers.io(),
            schedulerObserve: Scheduler = AndroidSchedulers.mainThread()
    ): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream
                    .subscribeOn(schedulerSubscribe)
                    .observeOn(schedulerObserve)
        }
    }

    protected fun applySchedulersToCompletable(
            schedulerSubscribe: Scheduler = Schedulers.io(),
            schedulerObserve: Scheduler = AndroidSchedulers.mainThread()
    ): CompletableTransformer {
        return CompletableTransformer { upstream ->
            upstream
                    .subscribeOn(schedulerSubscribe)
                    .observeOn(schedulerObserve)
        }
    }
}
