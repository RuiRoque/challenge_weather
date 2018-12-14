package com.challenge.weather.mvvm.model.repository.datasources.sharedpreferences

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class SharedPreferencesOperations @SuppressLint("CommitPrefEdits") constructor(
    val context: Context,
    sharedPreferencesName: String
) {

    private val sharedPreferences: SharedPreferences

    private val editor: SharedPreferences.Editor

    init {
        this.sharedPreferences = getSharedPreferences(sharedPreferencesName)
        this.editor = sharedPreferences.edit()
    }

    fun registerListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unregisterListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
    }

    fun containsKey(key: String): Boolean {
        return sharedPreferences.contains(key)
    }

    operator fun get(key: String, defaultValue: Boolean): Boolean {
        var value = defaultValue
        if (sharedPreferences.contains(key)) {
            value = sharedPreferences.getBoolean(key, defaultValue)
        }
        return value
    }

    operator fun get(key: String, defaultValue: Long?): Long? {
        var value = defaultValue
        if (sharedPreferences.contains(key)) {
            value = sharedPreferences.getLong(key, defaultValue!!)
        }
        return value
    }

    operator fun get(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue)?.let { it } ?: defaultValue
    }

    operator fun get(key: String, defaultValue: Set<String>): Set<String> {
        return sharedPreferences.getStringSet(key, defaultValue)?.let { it } ?: defaultValue
    }

    operator fun get(key: String, defaultValue: Boolean?): Boolean? {
        var value = defaultValue
        if (sharedPreferences.contains(key)) {
            value = sharedPreferences.getBoolean(key, defaultValue!!)
        }
        return value
    }

    operator fun get(key: String, defaultValue: Float?): Float? {
        var value = defaultValue
        if (sharedPreferences.contains(key)) {
            value = sharedPreferences.getFloat(key, defaultValue!!)
        }
        return value
    }

    operator fun get(key: String, defaultValue: Int?): Int? {
        var value = defaultValue
        if (sharedPreferences.contains(key)) {
            value = sharedPreferences.getInt(key, defaultValue!!)
        }
        return value
    }

    fun put(key: String, value: Long) {
        editor.putLong(key, value)
        editor.apply()
    }

    fun put(key: String, value: String) {
        editor.putString(key, value)
        editor.apply()
    }

    fun put(key: String, value: Set<String>) {
        editor.putStringSet(key, value)
        editor.apply()
    }

    fun put(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun put(key: String, value: Float) {
        editor.putFloat(key, value)
        editor.apply()
    }

    fun put(key: String, value: Int) {
        editor.putInt(key, value)
        editor.apply()
    }

    fun remove(key: String) {
        editor.remove(key)
        editor.apply()
    }

    fun clear() {
        editor.clear()
        editor.apply()
    }

    private fun getSharedPreferences(sharedPreferencesName: String?): SharedPreferences {
        return if (sharedPreferencesName != null) {
            context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)
        } else {
            PreferenceManager.getDefaultSharedPreferences(context)
        }
    }
}
