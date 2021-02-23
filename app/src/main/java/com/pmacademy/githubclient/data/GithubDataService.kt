package com.pmacademy.githubclient.data

import com.pmacademy.githubclient.data.models.Repository
import com.pmacademy.githubclient.data.models.User
import retrofit2.Call
import retrofit2.http.*

interface GithubDataService {
    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/user")
    fun getUser(@Header("Authorization") auth: String): Call<User>

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/users/{owner}/repos")
    fun getUserRepositories(@Path("owner") owner: String): Call<List<Repository>>
}