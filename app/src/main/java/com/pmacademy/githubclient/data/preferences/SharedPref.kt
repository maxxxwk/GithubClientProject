package com.pmacademy.githubclient.data.preferences

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {

    private companion object {
        const val KEY_TOKEN = "KEY_TOKEN"
        const val KEY_LAST = "KEY_LAST"
    }

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("test", Context.MODE_PRIVATE)
    }

    var token: String by SharedePreferencesDelegate(sharedPreferences, KEY_TOKEN, "")

    val bool: Boolean by SharedePreferencesDelegate(sharedPreferences, KEY_LAST, true)

}