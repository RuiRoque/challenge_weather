package com.challenge.weather.listing.model.repository.sharedpreferences

import com.challenge.weather.mvvm.model.repository.RepositoryManager
import com.challenge.weather.mvvm.model.repository.datasources.sharedpreferences.SharedPreferencesOperations

class SharedPreferencesLastQuery(private val repositoryManager: RepositoryManager) {

    var lastQuery: String
        get() = sharedPreferencesStagingHelper.get(SHARED_PREFERENCES_KEY_LAST_QUERY, "")
        set(lastQuery) = sharedPreferencesStagingHelper.put(SHARED_PREFERENCES_KEY_LAST_QUERY, lastQuery)

    private val sharedPreferencesStagingHelper: SharedPreferencesOperations by lazy {
        repositoryManager.sharedPreferences.getSharedPreferencesOperations(SHARED_PREFERENCES_LAST_QUERY)
    }

    companion object {
        private const val SHARED_PREFERENCES_LAST_QUERY = "SharedPreferencesLastQuery"
        private const val SHARED_PREFERENCES_KEY_LAST_QUERY = "LastQuery"
    }
}
