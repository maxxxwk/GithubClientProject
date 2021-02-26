package com.pmacademy.githubclient.utils

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthTokenSharedPref @Inject constructor(context: Context) {
    companion object {
        private const val ACCESS_TOKEN_KEY = "ACCESS_TOKEN_KEY"
        private const val TOKEN_TYPE_KEY = "TOKEN_TYPE_KEY"
        private const val SHARED_PREFERENCES_NAME = "SharedPreferences"
    }

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }
    var accessToken: String by SharedPreferencesDelegate(sharedPreferences, ACCESS_TOKEN_KEY, "")
    var tokenType: String by SharedPreferencesDelegate(sharedPreferences, TOKEN_TYPE_KEY, "")
}