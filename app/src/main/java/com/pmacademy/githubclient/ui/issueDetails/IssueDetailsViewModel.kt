package com.pmacademy.githubclient.ui.issueDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pmacademy.githubclient.data.GithubRepository
import com.pmacademy.githubclient.ui.State
import com.pmacademy.githubclient.utils.SharedPref
import java.util.concurrent.Executors
import javax.inject.Inject

class IssueDetailsViewModel @Inject constructor(
    private val githubRepository: GithubRepository,
    private val sharedPref: SharedPref
) : ViewModel() {
    private val _stateLiveData =
        MutableLiveData<State<IssueDetails, Error>>()
    val stateLiveData: LiveData<State<IssueDetails, Error>> =
        _stateLiveData
    private val executor = Executors.newSingleThreadExecutor()

    fun loadIssueDetails(
        repo: String,
        owner: String,
        number: Int
    ) {
        _stateLiveData.value = State.Loading
        executor.submit {
            _stateLiveData.postValue(
                githubRepository.getIssueDetails(
                    "${sharedPref.tokenType} ${sharedPref.accessToken}",
                    repo,
                    owner,
                    number
                )
            )
        }
    }
}