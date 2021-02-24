package com.pmacademy.githubclient.data

import com.pmacademy.githubclient.data.models.Repository
import com.pmacademy.githubclient.data.models.User
import com.pmacademy.githubclient.ui.State
import com.pmacademy.githubclient.ui.userInfo.UserInfo
import com.pmacademy.githubclient.ui.userInfo.UserInfoError
import javax.inject.Inject

class GithubUserInfoRepository @Inject constructor(
    private val githubDataService: GithubDataService
) {

    fun getUserInfo(authToken: String): State<UserInfo, UserInfoError> {
        var user: User?
        var repositories: List<Repository>?
        githubDataService.getUser(authToken).execute().let { userResponse ->
            if (!userResponse.isSuccessful) {
                return when (userResponse.code()) {
                    401 -> State.Error(UserInfoError.UNAUTHORIZED_ERROR)
                    404 -> State.Error(UserInfoError.NOT_FOUND_ERROR)
                    else -> State.Error(UserInfoError.LOADING_ERROR)
                }
            }
            user = userResponse.body()
        }
        githubDataService.getRepositoriesByToken(authToken).execute().let { repositoriesResponse ->
            if (!repositoriesResponse.isSuccessful) {
                return when (repositoriesResponse.code()) {
                    401 -> State.Error(UserInfoError.UNAUTHORIZED_ERROR)
                    404 -> State.Error(UserInfoError.NOT_FOUND_ERROR)
                    else -> State.Error(UserInfoError.LOADING_ERROR)
                }
            }
            repositories = repositoriesResponse.body()
        }
        user?.let { user ->
            repositories?.let { repositories ->
                return State.Content(UserInfo(user, repositories))
            }
        }
        return State.Error(UserInfoError.LOADING_ERROR)
    }

}