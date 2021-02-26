package com.pmacademy.githubclient.ui.repositoryDetails.readme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pmacademy.githubclient.data.GithubServiceRepository
import com.pmacademy.githubclient.ui.State
import com.pmacademy.githubclient.ui.Error
import java.util.concurrent.Executors
import javax.inject.Inject

class ReadmeViewModel @Inject constructor(
    private val githubServiceRepository: GithubServiceRepository
) : ViewModel() {
    private val _stateLiveData = MutableLiveData<State<String, Error>>()
    val stateLiveData: LiveData<State<String, Error>> = _stateLiveData
    private val executor = Executors.newSingleThreadExecutor()

    fun loadReadme(repoName: String, ownerName: String) {
        _stateLiveData.value = State.Loading
        executor.submit {
            _stateLiveData.postValue(
                githubServiceRepository.getReadme(repoName, ownerName)
            )
        }
    }
}