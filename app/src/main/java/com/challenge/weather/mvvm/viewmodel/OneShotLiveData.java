package com.challenge.weather.mvvm.viewmodel;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.util.Pair;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class OneShotLiveData<T> extends MutableLiveData<T> {

    private final ConcurrentHashMap<Observer<T>, Pair<Observer<T>, AtomicBoolean>> mPendingObservers = new ConcurrentHashMap<>();

    @MainThread
    public void observe(LifecycleOwner owner, Observer<T> observer) {
        Observer<T> interceptor = new Observer<T>() {
            @Override
            public void onChanged(@Nullable T t) {
                Observer<T> observerToTrigger = null;
                synchronized (mPendingObservers) {
                    if (mPendingObservers.containsKey(this) && mPendingObservers.get(this).second.compareAndSet(true, false)) {
                        observerToTrigger = mPendingObservers.get(this).first;
                    }
                }
                if (observerToTrigger != null) {
                    observerToTrigger.onChanged(t);
                }
            }
        };

        synchronized (mPendingObservers) {
            mPendingObservers.put(interceptor, Pair.create(observer, new AtomicBoolean(false)));
        }

        // Observe the internal MutableLiveData
        super.observe(owner, interceptor);
    }

    @Override
    public void removeObserver(Observer<T> observer) {
        super.removeObserver(observer);
        synchronized (mPendingObservers) {
            for (Map.Entry<Observer<T>, Pair<Observer<T>, AtomicBoolean>> entry : mPendingObservers.entrySet()) {
                if (observer == entry.getKey() || observer == entry.getValue().first) {
                    mPendingObservers.remove(entry.getKey());
                    break;
                }
            }
        }
    }

    @MainThread
    public void setValue(@Nullable T t) {
        synchronized (mPendingObservers) {
            for (Pair<Observer<T>, AtomicBoolean> pending : mPendingObservers.values()) {
                pending.second.set(true);
            }
        }
        super.setValue(t);
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    public void call() {
        setValue(null);
    }
}
