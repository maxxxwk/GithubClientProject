package com.pmacademy.githubclient.ui.repositoryDetails.issues

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pmacademy.githubclient.data.GithubRepository
import com.pmacademy.githubclient.data.models.Issue
import com.pmacademy.githubclient.ui.Error
import com.pmacademy.githubclient.ui.State
import com.pmacademy.githubclient.utils.SharedPref
import java.util.concurrent.Executors
import javax.inject.Inject

class IssuesListViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : ViewModel() {

    private val _stateLiveData = MutableLiveData<State<List<Issue>, Error>>()
    val stateLiveData: LiveData<State<List<Issue>, Error>> = _stateLiveData
    private val executor = Executors.newSingleThreadExecutor()

    fun loadIssues(repo: String, owner: String) {
        _stateLiveData.value = State.Loading
        executor.submit {
            _stateLiveData.postValue(
                githubRepository.getIssuesList(
                    repo,
                    owner
                )
            )
        }
    }
}