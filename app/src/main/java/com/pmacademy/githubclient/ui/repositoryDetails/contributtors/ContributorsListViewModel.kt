package com.pmacademy.githubclient.ui.repositoryDetails.contributtors

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pmacademy.githubclient.data.GithubServiceRepository
import com.pmacademy.githubclient.data.models.User
import com.pmacademy.githubclient.ui.State
import com.pmacademy.githubclient.ui.Error
import java.util.concurrent.Executors
import javax.inject.Inject

class ContributorsListViewModel @Inject constructor(
    private val githubServiceRepository: GithubServiceRepository
) : ViewModel() {
    private val _stateLiveData = MutableLiveData<State<List<User>, Error>>()
    val stateLiveData: LiveData<State<List<User>, Error>> = _stateLiveData
    private val executor = Executors.newSingleThreadExecutor()

    fun loadContributors(repoName: String, ownerName: String) {
        _stateLiveData.value = State.Loading
        executor.submit {
            _stateLiveData.postValue(
                githubServiceRepository.getContributors(
                    repoName,
                    ownerName
                )
            )
        }
    }

}