package com.pmacademy.githubclient.ui.userInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pmacademy.githubclient.data.GithubRepository
import com.pmacademy.githubclient.ui.State
import com.pmacademy.githubclient.ui.Error
import com.pmacademy.githubclient.utils.SharedPref
import java.util.concurrent.Executors
import javax.inject.Inject

class UserInfoViewModel @Inject constructor(
    private val githubRepository: GithubRepository,
    private val sharedPref: SharedPref
) : ViewModel() {
    private val _userInfoStateLiveData = MutableLiveData<State<UserInfo, Error>>()
    val stateLiveData: LiveData<State<UserInfo, Error>> = _userInfoStateLiveData
    private val executor = Executors.newSingleThreadExecutor()

    fun loadUserInfo(userName: String?) {
        _userInfoStateLiveData.value = State.Loading
        executor.submit {
            _userInfoStateLiveData.postValue(
                githubRepository.getUserInfo(userName)
            )
        }
    }
}