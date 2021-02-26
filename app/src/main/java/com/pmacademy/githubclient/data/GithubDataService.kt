package com.pmacademy.githubclient.data

import com.pmacademy.githubclient.data.models.*
import retrofit2.Call
import retrofit2.http.*

interface GithubDataService {
    @GET("/user")
    fun getUserByToken(@Header("Authorization") auth: String): Call<User>

    @GET("/users/{username}")
    fun getUser(
        @Header("Authorization") auth: String,
        @Path("username") userName: String
    ): Call<User>

    @GET("/users/{owner}/repos")
    fun getUserRepositories(@Path("owner") owner: String): Call<List<Repository>>

    @GET("/user/repos")
    fun getRepositoriesByToken(@Header("Authorization") auth: String): Call<List<Repository>>

    @GET("/repos/{owner}/{repo}/contributors")
    fun getContributors(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Call<List<User>>

    @GET("repos/{owner}/{repo}/readme")
    fun getReadme(
        @Header("Authorization") auth: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Call<Readme>

    @GET("/search/users")
    fun search(
        @Query("q") q: String
    ): Call<SearchUserResponse>

    @GET("/repos/{owner}/{repo}/issues")
    fun getIssues(
        @Header("Authorization") auth: String,
        @Path("repo") repo: String,
        @Path("owner") owner: String
    ): Call<List<Issue>>

    @GET("/repos/{owner}/{repo}/issues/{number}")
    fun getIssue(
        @Header("Authorization") auth: String,
        @Path("repo") repo: String,
        @Path("owner") owner: String,
        @Path("number") number: Int
    ): Call<Issue>

    @GET("/repos/{owner}/{repo}/issues/{number}/comments")
    fun getIssueComments(
        @Header("Authorization") auth: String,
        @Path("repo") repo: String,
        @Path("owner") owner: String,
        @Path("number") number: Int
    ): Call<List<IssueComment>>
}