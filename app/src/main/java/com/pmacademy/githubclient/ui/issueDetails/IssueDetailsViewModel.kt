package com.pmacademy.githubclient.ui.issueDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pmacademy.githubclient.data.GithubServiceRepository
import com.pmacademy.githubclient.ui.Error
import com.pmacademy.githubclient.ui.State
import java.util.concurrent.Executors
import javax.inject.Inject

class IssueDetailsViewModel @Inject constructor(
    private val githubServiceRepository: GithubServiceRepository
) : ViewModel() {
    private val _stateLiveData =
        MutableLiveData<State<IssueDetails, Error>>()
    val stateLiveData: LiveData<State<IssueDetails, Error>> =
        _stateLiveData
    private val executor = Executors.newSingleThreadExecutor()

    fun loadIssueDetails(
        repoName: String,
        ownerName: String,
        issueNumber: Int
    ) {
        _stateLiveData.value = State.Loading
        executor.submit {
            _stateLiveData.postValue(
                githubServiceRepository.getIssueDetails(
                    repoName,
                    ownerName,
                    issueNumber
                )
            )
        }
    }
}