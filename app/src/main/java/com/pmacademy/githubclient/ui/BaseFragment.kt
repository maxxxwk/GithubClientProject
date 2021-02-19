package com.pmacademy.githubclient.ui

import android.content.Intent
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.pmacademy.githubclient.GitHubUtils

import com.pmacademy.githubclient.data.preferences.SharedPref
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

abstract class BaseFragment(@LayoutRes layout: Int) : Fragment(layout) {

    protected val navigator: Navigator by lazy {
        (requireActivity() as NavigationActivity).navigator
    }
}