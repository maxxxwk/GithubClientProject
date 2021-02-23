package com.pmacademy.githubclient.ui

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.pmacademy.githubclient.ui.authorization.AuthFragment
import com.pmacademy.githubclient.ui.userInfo.UserInfoFragment

class Navigator(private val fragmentManager: FragmentManager,
                @IdRes private val container: Int) {
    companion object {
        const val AUTH_FRAGMENT = "AUTH_FRAGMENT"
        const val USER_INFO_FRAGMENT = "USER_INFO_FRAGMENT"
    }
    fun showAuthFragment() {
        fragmentManager.beginTransaction()
            .replace(container, AuthFragment.newInstance())
            .addToBackStack(AUTH_FRAGMENT)
            .commit()
    }

    fun showUserInfoFragment() {
        fragmentManager.beginTransaction()
            .replace(container, UserInfoFragment.newInstance())
            .addToBackStack(USER_INFO_FRAGMENT)
            .commit()
    }
}