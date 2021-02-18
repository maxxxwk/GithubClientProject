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
    private val sharedPreferences by lazy {
        context?.let { SharedPref(it) }
    }

    private val githubUtils: GitHubUtils by lazy {
        GitHubUtils()
    }

    //Create Button to start startGitHubLogin()

    private fun startGitHubLogin() {
        val authIntent = Intent(Intent.ACTION_VIEW, githubUtils.buildAuthGitHubUrl())
        startActivity(authIntent)
    }

    override fun onResume() {
        super.onResume()

    }
}