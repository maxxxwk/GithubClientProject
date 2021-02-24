package com.pmacademy.githubclient.data

import com.pmacademy.githubclient.data.models.Repository
import com.pmacademy.githubclient.data.models.User
import retrofit2.Call
import retrofit2.http.*

interface GithubDataService {
    @GET("/user")
    fun getUser(@Header("Authorization") auth: String): Call<User>

    @GET("/users/{owner}/repos")
    fun getUserRepositories(@Path("owner") owner: String): Call<List<Repository>>

    @GET("/user/repos")
    fun getUserByToken(@Header("Authorization") auth: String): Call<List<User>>

    @GET("/repos/{owner}/{repo}/contributors")
    fun getContributors(
        @Header("Authorization") auth: String
    ): Call<User>

    @GET("/avatar_url")
    fun getAvatars(@Header("Authorization") auth: String): User

    @GET("/emojis")
    fun getEmojis(@Header("Authorization") auth: String): User

    @GET("/{owner}/{repo}/master/README.md")
    fun getReadme(@Header("Authorization") auth: String): String

    @GET("/repos/{owner}/{repo}/pulls")
    fun getPulls(@Header("Authorization") auth: String): User

    @GET("/orgs/{org}/packages/{package_type}/{package_name}")
    fun getPackeges(@Header("Authorization") auth: String): User


    @GET("/orgs/{org}/issues")
    fun getIssues(@Header("Authorization") auth: String): String

}