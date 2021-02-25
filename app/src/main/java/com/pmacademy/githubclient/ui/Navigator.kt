package com.pmacademy.githubclient.ui

import android.util.Log
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.pmacademy.githubclient.data.models.Issue
import com.pmacademy.githubclient.data.models.Repository
import com.pmacademy.githubclient.ui.authorization.AuthFragment
import com.pmacademy.githubclient.ui.issueDetails.IssueDetailsFragment
import com.pmacademy.githubclient.ui.repositoryDetails.RepositoryDetailsFragment
import com.pmacademy.githubclient.ui.userInfo.UserInfoFragment

class Navigator(
    private val fragmentManager: FragmentManager,
    @IdRes private val container: Int
) {
    companion object {
        private const val USER_INFO_FRAGMENT = "USER_INFO_FRAGMENT"
        private const val AUTH_FRAGMENT = "AUTH_FRAGMENT"
        private const val ISSUE_DETAILS_FRAGMENT = "ISSUE_DETAILS_FRAGMENT"
        private const val REPOSITORY_DETAILS_FRAGMENT = "REPOSITORY_DETAILS_FRAGMENT"
    }

    fun showAuthFragment() {
        fragmentManager.beginTransaction()
            .add(container, AuthFragment.newInstance())
            .addToBackStack(AUTH_FRAGMENT)
            .commit()
    }

    fun showUserInfoFragment(userName: String?) {
        fragmentManager.beginTransaction()
            .add(container, UserInfoFragment.newInstance(userName))
            .addToBackStack(USER_INFO_FRAGMENT)
            .commit()
    }

    fun showIssueDetailsFragment(issue: Issue, reposName: String, reposOwner: String) {
        fragmentManager.beginTransaction()
            .add(container, IssueDetailsFragment.newInstance(issue, reposName, reposOwner))
            .addToBackStack(ISSUE_DETAILS_FRAGMENT)
            .commit()
    }

    fun showRepositoryDetailsFragment(repository: Repository) {
        fragmentManager.beginTransaction()
            .add(container, RepositoryDetailsFragment.newInstance(repository))
            .addToBackStack(REPOSITORY_DETAILS_FRAGMENT)
            .commit()
    }
}