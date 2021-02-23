package com.pmacademy.githubclient.ui.userInfo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pmacademy.githubclient.data.GithubUserInfoRepository
import com.pmacademy.githubclient.ui.State
import com.pmacademy.githubclient.utils.SharedPref
import java.util.concurrent.Executors
import javax.inject.Inject

class UserInfoViewModel @Inject constructor(
    private val githubUserInfoRepository: GithubUserInfoRepository,
    private val sharedPref: SharedPref
) : ViewModel() {
    private val _userInfoStateLiveData = MutableLiveData<State<UserInfo, UserInfoError>>()
    val userInfoStateLiveData: LiveData<State<UserInfo, UserInfoError>> = _userInfoStateLiveData
    private val executor = Executors.newSingleThreadExecutor()

    fun loadUserInfo() {
        _userInfoStateLiveData.value = State.Loading
        executor.submit {
            _userInfoStateLiveData.postValue(githubUserInfoRepository.getUserInfo(sharedPref.token))
        }
    }
}