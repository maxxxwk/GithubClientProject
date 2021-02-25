package com.pmacademy.githubclient.ui

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.pmacademy.githubclient.ui.authorization.AuthFragment
import com.pmacademy.githubclient.ui.issueDetails.IssueDetailsFragment
import com.pmacademy.githubclient.ui.userInfo.UserInfoFragment

class Navigator(
    private val fragmentManager: FragmentManager,
    @IdRes private val container: Int
) {
    companion object {
        private const val USER_INFO_FRAGMENT = "USER_INFO_FRAGMENT"
        private const val AUTH_FRAGMENT = "AUTH_FRAGMENT"
        private const val ISSUE_DETAILS_FRAGMENT = "ISSUE_DETAILS_FRAGMENT"
    }

    fun showAuthFragment() {
        fragmentManager.beginTransaction()
            .add(container, AuthFragment.newInstance())
            .addToBackStack(AUTH_FRAGMENT)
            .commit()
    }

    fun showUserInfoFragment() {
        fragmentManager.beginTransaction()
            .add(container, UserInfoFragment.newInstance())
            .addToBackStack(USER_INFO_FRAGMENT)
            .commit()
    }

    fun showIssueDetailsFragment(repos: String, owner: String, number: Int) {
        fragmentManager.beginTransaction()
            .add(container, IssueDetailsFragment.newInstance(repos, owner, number))
            .addToBackStack(ISSUE_DETAILS_FRAGMENT)
            .commit()
    }
}