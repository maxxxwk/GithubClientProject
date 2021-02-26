package com.pmacademy.githubclient.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pmacademy.githubclient.ui.issueDetails.IssueDetailsViewModel
import com.pmacademy.githubclient.ui.repositoryDetails.contributtors.ContributorsListFragment
import com.pmacademy.githubclient.ui.repositoryDetails.contributtors.ContributorsListViewModel
import com.pmacademy.githubclient.ui.repositoryDetails.issues.IssuesListViewModel
import com.pmacademy.githubclient.ui.repositoryDetails.readme.ReadmeViewModel
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

    @Binds
    @IntoMap
    @ViewModelKey(IssueDetailsViewModel::class)
    abstract fun issueDetailsViewModel(viewModel: IssueDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IssuesListViewModel::class)
    abstract fun issuesListViewModel(viewModel: IssuesListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ContributorsListViewModel::class)
    abstract fun contributorsViewModel(viewModel: ContributorsListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ReadmeViewModel::class)
    abstract fun readmeViewModel(viewModel: ReadmeViewModel): ViewModel
}