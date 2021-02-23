package com.pmacademy.githubclient.utils

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SharedPreferencesDelegate <T>(
    private val sharedPreferences: SharedPreferences,
    private val key: String,
    private val defValue: T,
) : ReadWriteProperty<Any?, T> {

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = with(sharedPreferences.edit()) {
        when (value) {
            is String -> putString(key, value)
            is Boolean -> putBoolean(key, value)
            is Int -> putInt(key,value)
            else -> throw IllegalAccessError("")
        }.apply()
    }

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Any?, property: KProperty<*>): T = with(sharedPreferences) {
        return when (defValue) {
            is String -> getString(key, defValue)
            is Boolean -> getBoolean(key, defValue)
            is Int -> getInt(key,defValue)
            else -> null
        } as T
    }
}