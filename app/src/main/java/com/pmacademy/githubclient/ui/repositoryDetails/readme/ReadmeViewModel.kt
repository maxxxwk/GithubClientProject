package com.pmacademy.githubclient.ui.repositoryDetails.readme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pmacademy.githubclient.data.GithubRepository
import com.pmacademy.githubclient.ui.State
import com.pmacademy.githubclient.ui.Error
import java.util.concurrent.Executors
import javax.inject.Inject

class ReadmeViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : ViewModel() {
    private val _stateLiveData = MutableLiveData<State<String, Error>>()
    val stateLiveData: LiveData<State<String, Error>> = _stateLiveData
    private val executor = Executors.newSingleThreadExecutor()

    fun loadReadme(repo: String, owner: String) {
        _stateLiveData.value = State.Loading
        executor.submit {
            _stateLiveData.postValue(
                githubRepository.getReadme(repo, owner)
            )
        }
    }
}