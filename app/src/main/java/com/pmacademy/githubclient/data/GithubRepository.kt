package com.pmacademy.githubclient.data

import com.pmacademy.githubclient.data.models.*
import com.pmacademy.githubclient.ui.State
import com.pmacademy.githubclient.ui.issueDetails.IssueDetails
import com.pmacademy.githubclient.ui.userInfo.UserInfo
import com.pmacademy.githubclient.ui.issueDetails.Error
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val githubDataService: GithubDataService
) {

    fun getUserInfo(authToken: String): State<UserInfo, Error> {
        var user: User?
        var repositories: List<Repository>?
        githubDataService.getUser(authToken).execute().let { userResponse ->
            if (!userResponse.isSuccessful) {
                return getErrorByResponseCode(userResponse.code())
            }
            user = userResponse.body()
        }
        githubDataService.getRepositoriesByToken(authToken).execute().let { repositoriesResponse ->
            if (!repositoriesResponse.isSuccessful) {
                return getErrorByResponseCode(repositoriesResponse.code())
            }
            repositories = repositoriesResponse.body()
        }
        user?.let { user ->
            repositories?.let { repositories ->
                return State.Content(UserInfo(user, repositories))
            }
        }
        return State.Error(Error.LOADING_ERROR)
    }

    fun getIssueDetails(
        authToken: String,
        repo: String,
        owner: String,
        number: Int
    ): State<IssueDetails, Error> {
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

    private fun <T> getErrorByResponseCode(code: Int): State<T, Error> = when (code) {
        401 -> State.Error(Error.UNAUTHORIZED_ERROR)
        404 -> State.Error(Error.NOT_FOUND_ERROR)
        else -> State.Error(Error.LOADING_ERROR)
    }

    fun getContributors(authToken: String,repo: String,owner: String): State<User, Error> {
        var contributor: List<User>?
        githubDataService.getContributors(authToken,repo,owner).execute().let { contributorResponse ->
            if (!contributorResponse.isSuccessful) {
                return getErrorByResponseCode(contributorResponse.code())
            }
            contributor = contributorResponse.body()
        }
        return State.Error(Error.LOADING_ERROR)
    }
}