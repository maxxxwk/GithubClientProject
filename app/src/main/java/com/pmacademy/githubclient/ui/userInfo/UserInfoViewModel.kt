package com.pmacademy.githubclient.ui.userInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pmacademy.githubclient.data.GithubServiceRepository
import com.pmacademy.githubclient.data.models.User
import com.pmacademy.githubclient.ui.State
import com.pmacademy.githubclient.ui.Error
import java.util.concurrent.Executors
import javax.inject.Inject

class UserInfoViewModel @Inject constructor(
    private val githubServiceRepository: GithubServiceRepository
) : ViewModel() {
    private val _userInfoStateLiveData = MutableLiveData<State<UserInfo, Error>>()
    val stateLiveData: LiveData<State<UserInfo, Error>> = _userInfoStateLiveData

    private val _searchUsersStateLiveData = MutableLiveData<List<User>>()
    val searchUsersStateLiveData: LiveData<List<User>> = _searchUsersStateLiveData

    private val executor = Executors.newFixedThreadPool(2)

    fun loadUserInfo(userName: String?) {
        _userInfoStateLiveData.value = State.Loading
        executor.submit {
            _userInfoStateLiveData.postValue(
                githubServiceRepository.getUserInfo(userName)
            )
        }
    }

    fun searchUsers(q: String) {
        executor.submit {
            _searchUsersStateLiveData.postValue(
                githubServiceRepository.searchUsers(q)
            )
        }
    }
}