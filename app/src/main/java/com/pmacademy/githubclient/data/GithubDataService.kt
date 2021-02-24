package com.pmacademy.githubclient.data

import com.pmacademy.githubclient.data.models.*
import retrofit2.Call
import retrofit2.http.*

interface GithubDataService {
    @GET("/user")
    fun getUser(@Header("Authorization") auth: String): Call<User>

    @GET("/users/{owner}/repos")
    fun getUserRepositories(@Path("owner") owner: String): Call<List<Repository>>

    @GET("/user/repos")
    fun getRepositoriesByToken(@Header("Authorization") auth: String): Call<List<Repository>>

    @GET("/repos/{owner}/{repo}/contributors")
    fun getContributors(
        @Header("Authorization") auth: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Call<List<User>>

    @GET("/emojis")
    fun getEmojis(@Header("Authorization") auth: String): Call<List<Emoji>>


    @GET("/{owner}/{repo}/master/README.md")
    fun getReadme(
        @Header("Authorization") auth: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Call<String>

    @GET("/repos/{owner}/{repo}/pulls")
    fun getPulls(
        @Header("Authorization") auth: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Call<List<PullRequest>>

    @GET("/search/users")
    fun search(
        @Query("q") q: Call<List<SearchUser>>
    )

    @GET("/repos/{owner}/{repo}/issues")
    fun getIssues(
        @Header("Authorization") auth: String,
        @Path("repo") repo: String,
        @Path("owner") owner: String
    ): Call<List<Issue>>

    @GET("/repos/{owner}/{repo}/issues")
    fun getRepositoryIssues(
        @Header("Authorization") auth: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Call<ListRepositoryIssues>

    @GET("/repos/{owner}/{repo}/issues/{issue_number}")
    fun getRepositoryIssuesDeteils(
        @Header("Authorization") auth: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Call<ListRepositoryIssuesDetails>

    @GET("/repos/{owner}/{repo}/issues/comments/{comment_id}")
    fun getIssueComment(
        @Header("Authorization") auth: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("comment_id") comment_id:Int
    )
}