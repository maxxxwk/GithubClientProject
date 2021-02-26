package com.pmacademy.githubclient.di

import com.pmacademy.githubclient.ui.NavigationActivity
import com.pmacademy.githubclient.ui.issueDetails.IssueDetailsFragment
import com.pmacademy.githubclient.ui.repositoryDetails.contributtors.ContributorsListFragment
import com.pmacademy.githubclient.ui.repositoryDetails.issues.IssuesListFragment
import com.pmacademy.githubclient.ui.repositoryDetails.readme.ReadmeFragment
import com.pmacademy.githubclient.ui.repositoryDetails.readme.ReadmeViewModel
import com.pmacademy.githubclient.ui.userInfo.UserInfoFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(activity: NavigationActivity)
    fun inject(fragment: UserInfoFragment)
    fun inject(fragment: IssueDetailsFragment)
    fun inject(fragment: IssuesListFragment)
    fun inject(fragment: ContributorsListFragment)
    fun inject(readmeFragment: ReadmeFragment)
}