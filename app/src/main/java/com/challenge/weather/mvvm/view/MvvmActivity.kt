package com.challenge.weather.mvvm.view

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity


abstract class MvvmActivity<DB : ViewDataBinding> : DaggerAppCompatActivity() {

    protected lateinit var dataBinding: DB

    abstract val layoutResId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeDataBinding()
    }

    private fun initializeDataBinding() {
        dataBinding = DataBindingUtil.setContentView(this, layoutResId)
    }
}
