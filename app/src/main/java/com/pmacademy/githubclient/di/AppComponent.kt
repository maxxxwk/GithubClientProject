package com.pmacademy.githubclient.di

import com.pmacademy.githubclient.ui.NavigationActivity
import com.pmacademy.githubclient.ui.issueDetails.IssueDetailsFragment
import com.pmacademy.githubclient.ui.projectDetails.ProjectDetailfFragment
import com.pmacademy.githubclient.ui.userInfo.UserInfoFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(activity: NavigationActivity)
    fun inject(fragment: UserInfoFragment)
    fun inject(fragment: IssueDetailsFragment)
    fun inject(fragment: ProjectDetailfFragment)
}