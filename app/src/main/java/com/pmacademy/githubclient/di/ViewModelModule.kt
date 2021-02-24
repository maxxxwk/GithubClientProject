package com.pmacademy.githubclient.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pmacademy.githubclient.ui.userInfo.UserInfoViewModel
import com.pmacademy.githubclient.utils.viewModelFactory.ViewModelFactory
import com.pmacademy.githubclient.utils.viewModelFactory.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(UserInfoViewModel::class)
    abstract fun userInfoViewModel(viewModel: UserInfoViewModel): ViewModel
}