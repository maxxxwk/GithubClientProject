package com.pmacademy.githubclient.ui

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.pmacademy.githubclient.ui.authorization.AuthFragment

class Navigator(private val fragmentManager: FragmentManager,
                @IdRes private val container: Int) {
    fun showAuthFragment() {
        fragmentManager.beginTransaction()
            .replace(container, AuthFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }
}