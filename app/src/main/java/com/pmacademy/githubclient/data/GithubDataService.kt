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
    fun getRepositoriesByToken(@Header("Authorization") auth: String): Call<List<Repository>>

    @GET("/repos/{owner}/{repo}/contributors")
    fun getContributors(
        @Header("Authorization") auth: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Call<List<User>>

    @GET("/avatar_url")
    fun getAvatars(@Header("Authorization") auth: String): Call<String>


//    @GET("/emojis")
//    fun getEmojis(@Header("Authorization") auth: String): User - should return Call<List<Emoji>>


    @GET("/{owner}/{repo}/master/README.md")
    fun getReadme(
        @Header("Authorization") auth: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Call<String>

//    @GET("/repos/{owner}/{repo}/pulls")
//    fun getPulls(
//        @Header("Authorization") auth: String,
//        @Path("owner") owner: String,
//        @Path("repo") repo: String
//    ): User - should return Call<List<PullRequest>>

//    @GET("/orgs/{org}/packages/{package_type}/{package_name}")
//    fun getPackeges(@Header("Authorization") auth: String): User - We don`t need packages......


//    @GET("/orgs/{org}/issues")
//    fun getIssues(
//        @Header("Authorization") auth: String,
//        @Path("orgs") orgs: String
//    ): String - Should return Call<List<Issue>>

}