package com.pmacademy.githubclient.data

import com.pmacademy.githubclient.ui.State
import com.pmacademy.githubclient.ui.userInfo.UserInfo
import com.pmacademy.githubclient.ui.userInfo.UserInfoError
import javax.inject.Inject

class GithubUserInfoRepository @Inject constructor(
    private val githubDataService: GithubDataService
) {

    fun getUserInfo(authToken: String): State<UserInfo, UserInfoError> {
        githubDataService.getUser(authToken).execute().let { userResponse ->
            if (!userResponse.isSuccessful) {
                return when (userResponse.code()) {
                    401 -> State.Error(UserInfoError.UNAUTHORIZED_ERROR)
                    404 -> State.Error(UserInfoError.NOT_FOUND_ERROR)
                    else -> State.Error(UserInfoError.LOADING_ERROR)
                }
            }
            userResponse.body()?.let { user ->
                githubDataService.getUserRepositories(user.login).execute()
                    .let { repositoriesResponse ->
                        val repositories = repositoriesResponse.body() ?: emptyList()
                        return State.Content(UserInfo(user, repositories))
                    }
            }
        }
        return State.Error(UserInfoError.LOADING_ERROR)
    }

}