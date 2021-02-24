package com.pmacademy.githubclient.data.models

import com.google.gson.annotations.SerializedName

data class ListRepositoryIssuesDetails(
    @SerializedName("number") val number: Int,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String,
    @SerializedName("user") val user: User,
)