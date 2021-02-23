package com.pmacademy.githubclient.utils

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPref @Inject constructor(context: Context) {
    companion object {
        private const val ACCESS_TOKEN_KEY = "ACCESS_TOKEN_KEY"
        private const val REFRESH_TOKEN_KEY = "REFRESH_TOKEN_KEY"
        private const val TOKEN_TYPE_KEY = "TOKEN_TYPE_KEY"
        private const val SHARED_PREFERENCES_NAME = "SharedPreferences"
    }

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }
    var accessToken: String by SharedPreferencesDelegate(sharedPreferences, ACCESS_TOKEN_KEY, "")
    var refreshToken: String by SharedPreferencesDelegate(sharedPreferences, REFRESH_TOKEN_KEY, "")
    var token_type: String by SharedPreferencesDelegate(sharedPreferences, TOKEN_TYPE_KEY, "")
}