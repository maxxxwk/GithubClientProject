package com.pmacademy.githubclient.data

import retrofit2.http.*

interface GitHubService {
    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    suspend fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code: String,
    ): AccessToken

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/user")
    suspend fun getUser(@Header("Authorization") auth: String): User

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/repos/{owner}/{repo}")
    suspend fun getRepos(@Header("Authorization") auth: String): User

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/avatar_url")
    suspend fun getAvatars(@Header("Authorization")auth: String):User

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/repos/{owner}/{repo}/contributors")
    suspend fun getContributors (@Header("Authorization")auth: String):User

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/emojis")
    suspend fun getEmojis (@Header("Authorization")auth: String):User

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/{owner}/{repo}/master/README.md")
    suspend fun getReadme(@Header("Authorization")auth: String):String

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/repos/{owner}/{repo}/pulls")
    suspend fun getPulls(@Header("Authorization")auth: String):User

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/orgs/{org}/packages/{package_type}/{package_name}")
    suspend fun getPackeges(@Header("Authorization")auth: String):User

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/orgs/{org}/issues")
    suspend fun getIssues(@Header("Authorization")auth: String):User
}