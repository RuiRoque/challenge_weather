package com.challenge.weather.listing.view.helpers

import android.view.View

object ViewHelper {

    fun show(vararg views: View) {
        changeVisibility(true, *views)
    }

    fun hide(vararg views: View) {
        changeVisibility(false, *views)
    }

    private fun changeVisibility(show: Boolean, vararg views: View) {
        views.forEach { view -> changeVisibility(view, show) }
    }

    private fun changeVisibility(view: View?, show: Boolean) {
        view?.visibility = if (show) View.VISIBLE else View.GONE
    }
}
