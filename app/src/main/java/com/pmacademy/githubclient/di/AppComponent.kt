package com.pmacademy.githubclient.di

import com.pmacademy.githubclient.ui.NavigationActivity
import com.pmacademy.githubclient.ui.userInfo.UserInfoFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(fragment: NavigationActivity)
    fun inject(fragment: UserInfoFragment)
}