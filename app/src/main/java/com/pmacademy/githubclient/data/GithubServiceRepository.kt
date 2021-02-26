package com.pmacademy.githubclient.data

import com.pmacademy.githubclient.ui.State
import com.pmacademy.githubclient.ui.issueDetails.IssueDetails
import com.pmacademy.githubclient.ui.userInfo.UserInfo
import com.pmacademy.githubclient.ui.Error
import com.pmacademy.githubclient.utils.AuthTokenSharedPref
import android.util.Base64
import com.pmacademy.githubclient.data.models.*
import javax.inject.Inject

class GithubServiceRepository @Inject constructor(
    private val githubDataService: GithubDataService,
    private val authTokenSharedPref: AuthTokenSharedPref
) {

    fun getUserInfo(userName: String?): State<UserInfo, Error> {
        val authToken = "${authTokenSharedPref.tokenType} ${authTokenSharedPref.accessToken}"
        var user: User?
        var repositories: List<Repository>?
        if (userName == null) {
            githubDataService.getUserByToken(authToken).execute().let { userResponse ->
                if (!userResponse.isSuccessful) {
                    return getErrorByResponseCode(userResponse.code())
                }
                user = userResponse.body()
            }
            githubDataService.getRepositoriesByToken(authToken).execute()
                .let { repositoriesResponse ->
                    if (!repositoriesResponse.isSuccessful) {
                        return getErrorByResponseCode(repositoriesResponse.code())
                    }
                    repositories = repositoriesResponse.body()
                }
        } else {
            githubDataService.getUser(authToken, userName).execute().let { userResponse ->
                if (!userResponse.isSuccessful) {
                    return getErrorByResponseCode(userResponse.code())
                }
                user = userResponse.body()
            }
            githubDataService.getUserRepositories(userName).execute().let { repositoriesResponse ->
                if (!repositoriesResponse.isSuccessful) {
                    return getErrorByResponseCode(repositoriesResponse.code())
                }
                repositories = repositoriesResponse.body()
            }
        }
        user?.let { user ->
            repositories?.let { repositories ->
                return State.Content(UserInfo(user, repositories))
            }
        }
        return State.Error(Error.LOADING_ERROR)
    }

    fun getIssueDetails(
        repo: String,
        owner: String,
        number: Int
    ): State<IssueDetails, Error> {
        val authToken = "${authTokenSharedPref.tokenType} ${authTokenSharedPref.accessToken}"
        var issue: Issue?
        var comments: List<IssueComment>?
        githubDataService.getIssue(authToken, repo, owner, number).execute().let { issueResponse ->
            if (!issueResponse.isSuccessful) {
                return getErrorByResponseCode(issueResponse.code())
            }
            issue = issueResponse.body()
        }
        githubDataService.getIssueComments(authToken, repo, owner, number).execute()
            .let { commentsResponse ->
                if (!commentsResponse.isSuccessful) {
                    return getErrorByResponseCode(commentsResponse.code())
                }
                comments = commentsResponse.body()
            }
        issue?.let { issue ->
            comments?.let { comments ->
                return State.Content(IssueDetails(issue, comments))
            }
        }
        return State.Error(Error.LOADING_ERROR)
    }

    fun getIssuesList(repo: String, owner: String): State<List<Issue>, Error> {
        val authToken = "${authTokenSharedPref.tokenType} ${authTokenSharedPref.accessToken}"
        githubDataService.getIssues(authToken, repo, owner).execute().let { issuesResponse ->
            if (!issuesResponse.isSuccessful) {
                return getErrorByResponseCode(issuesResponse.code())
            }
            issuesResponse.body()?.let { issues ->
                return State.Content(issues)
            }
        }
        return State.Error(Error.LOADING_ERROR)
    }

    fun getContributors(
        repo: String,
        owner: String
    ): State<List<User>, Error> {
        githubDataService.getContributors(owner, repo).execute()
            .let { contributorsResponse ->
                if (!contributorsResponse.isSuccessful) {
                    return getErrorByResponseCode(contributorsResponse.code())
                }
                contributorsResponse.body()?.let { contributors ->
                    return State.Content(contributors)
                }
            }
        return State.Error(Error.LOADING_ERROR)
    }

    fun getReadme(
        repo: String,
        owner: String
    ): State<String, Error> {
        val authToken = "${authTokenSharedPref.tokenType} ${authTokenSharedPref.accessToken}"
        githubDataService.getReadme(authToken, owner, repo).execute().let { readmeResponse ->
            if (!readmeResponse.isSuccessful) {
                return getErrorByResponseCode(readmeResponse.code())
            }
            readmeResponse.body()?.let { readme ->
                return State.Content(
                    Base64.decode(readme.content, 0).decodeToString()
                )
            }
        }
        return State.Error(Error.LOADING_ERROR)
    }

    fun searchUsers(q: String): List<User> {
        githubDataService.search(q).execute().let { searchResponse ->
            if (!searchResponse.isSuccessful) {
                return emptyList()
            }
            searchResponse.body()?.let { searchUserResponse ->
                return searchUserResponse.items
            }
        }
        return emptyList()
    }

    private fun <T> getErrorByResponseCode(code: Int): State<T, Error> = when (code) {
        401 -> State.Error(Error.UNAUTHORIZED_ERROR)
        403 -> State.Error(Error.FORBIDDEN_ERROR)
        404 -> State.Error(Error.NOT_FOUND_ERROR)
        else -> State.Error(Error.LOADING_ERROR)
    }
}