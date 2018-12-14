package com.challenge.weather.mvvm.model.repository

import com.challenge.weather.mvvm.model.repository.datasources.sharedpreferences.SharedPreferencesOperations
import java.util.HashMap

class RepositoryCache {

    private val retrofitServicesCache: MutableMap<String, Any> = HashMap()
    private val roomDatabaseServiceCache: MutableMap<String, Any> = HashMap()
    private val sharedPreferencesCache: MutableMap<String, SharedPreferencesOperations> = HashMap()

    fun getRetrofitServicesFromCache(name: String): Any? {
        return retrofitServicesCache[name]
    }

    fun storeRetrofitServicesInCache(name: String, retrofitService: Any) {
        retrofitServicesCache[name] = retrofitService
    }

    fun getRoomDatabaseFromCache(name: String): Any? {
        return roomDatabaseServiceCache[name]
    }

    fun storeRoomDatabaseInCache(name: String, roomDatabase: Any) {
        roomDatabaseServiceCache[name] = roomDatabase
    }

    fun getSharedPreferencesFromCache(name: String): SharedPreferencesOperations? {
        return sharedPreferencesCache[name]
    }

    fun storeSharedPreferencesInCache(sharedPreferencesName: String, sharedPreferencesOperations: SharedPreferencesOperations) {
        sharedPreferencesCache[sharedPreferencesName] = sharedPreferencesOperations
    }
}
