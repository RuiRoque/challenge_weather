package com.challenge.weather.listing.view

import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import com.challenge.weather.R
import com.challenge.weather.databinding.ActivityMainBinding
import com.challenge.weather.listing.view.helpers.ViewHelper
import com.challenge.weather.listing.viewmodel.WeatherViewModel
import com.challenge.weather.listing.viewmodel.entities.WeatherValues
import com.challenge.weather.listing.viewmodel.viewdata.WeatherViewData
import com.challenge.weather.mvvm.view.MvvmActivity
import dagger.android.AndroidInjection
import javax.inject.Inject


class MainActivity : MvvmActivity<ActivityMainBinding>() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: WeatherViewModel

    override val layoutResId: Int = R.layout.activity_main

    private val errorView: View by lazy { dataBinding.activityMainError.errorRoot }
    private val loadingView: View by lazy { dataBinding.activityMainLoading.loadingRoot }
    private val contentView: View by lazy { dataBinding.activityMainContainer }

    //region Activity lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)

        setupActionBar()

        initErrorView()

        initializeViewState(viewHasNoState(savedInstanceState))

        handleIntent(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

        return true
    }

    override fun onNewIntent(intent: Intent) {
        handleIntent(intent)
    }

    //endregion

    //region Aux methods

    private fun setupActionBar() {
        setSupportActionBar(dataBinding.activityMainToolbar.mainToolbar)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(false)
            it.setTitle(R.string.app_name)
        }
    }

    private fun initializeViewState(viewHasNoState: Boolean) {
        createViewModel()
        observeLiveData()

        if (viewHasNoState) {
            requestUserLocationWeather()
        }
    }

    private fun viewHasNoState(savedInstanceState: Bundle?) = savedInstanceState == null

    private fun requestUserLocationWeather() {
        val simulateLocation = "Lisbon,pt"
        loadData(simulateLocation)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                loadData(query)
            }
        }
    }

    private fun initErrorView() {
        dataBinding.activityMainError.errorRetryButton.setOnClickListener {
            loadData(viewModel.lastQuery)
            showView(ViewType.VIEW_LOADING)
        }
    }

    private fun createViewModel(){
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(WeatherViewModel::class.java)
    }

    private fun observeLiveData() {
        viewModel.liveData.observe(this, Observer { listingViewData ->
            when(listingViewData?.event) {
                WeatherViewData.Event.ShowLoading -> showLoading()
                WeatherViewData.Event.ShowNotFound -> showNotFound()
                WeatherViewData.Event.ShowError -> showError()
                WeatherViewData.Event.ShowResults -> showResults(listingViewData.weather)
            }
        })
    }

    private fun showNotFound() = showView(ViewType.VIEW_NOT_FOUND)
    private fun showError() = showView(ViewType.VIEW_ERROR)
    private fun showLoading() = showView(ViewType.VIEW_LOADING)

    private fun showResults(weatherValues: WeatherValues) {
        populateView(weatherValues)
        showView(ViewType.VIEW_CONTENT)
    }

    private fun populateView(weatherResponse: WeatherValues) {
        dataBinding.weather = weatherResponse
    }

    private fun loadData(query: String) {
        viewModel.getListing(query)
    }

    @Synchronized
    fun showView(view: ViewType) {
        when (view) {
            ViewType.VIEW_LOADING -> {
                ViewHelper.show(loadingView)
                ViewHelper.hide(errorView, contentView)
            }
            ViewType.VIEW_ERROR -> {
                ViewHelper.show(errorView)
                ViewHelper.hide(loadingView, contentView)
            }
            ViewType.VIEW_CONTENT -> {
                ViewHelper.show(contentView)
                ViewHelper.hide(errorView, loadingView)
            }
            ViewType.VIEW_NOT_FOUND -> {
                Snackbar.make(dataBinding.activityMainContainer, R.string.activity_main_not_found, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    //endregion

    enum class ViewType {
        VIEW_LOADING, VIEW_NOT_FOUND, VIEW_ERROR, VIEW_CONTENT
    }
}
