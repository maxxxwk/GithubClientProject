package com.pmacademy.githubclient.ui.projectDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pmacademy.githubclient.data.GithubRepository
import com.pmacademy.githubclient.data.models.Contributors
import com.pmacademy.githubclient.data.models.User
import com.pmacademy.githubclient.ui.State
import com.pmacademy.githubclient.ui.issueDetails.Error
import com.pmacademy.githubclient.ui.userInfo.UserInfo
import com.pmacademy.githubclient.utils.SharedPref
import java.util.concurrent.Executors
import javax.inject.Inject

class ProjectViewModel @Inject constructor(
    private val githubRepository: GithubRepository,
    private val sharedPref: SharedPref
) : ViewModel() {
    private val _userInfoStateLiveData = MutableLiveData<State<User, Error>>()
    val stateLiveData: LiveData<State<User, Error>> = _userInfoStateLiveData
    private val executor = Executors.newSingleThreadExecutor()

    fun loadUserInfo(
        repo: String,
        owner: String,
    ) {
        _userInfoStateLiveData.value = State.Loading
        executor.submit {
            _userInfoStateLiveData.postValue(
                githubRepository.getContributors("${sharedPref.tokenType} ${sharedPref.accessToken}",repo,owner)
            )
        }
    }
}