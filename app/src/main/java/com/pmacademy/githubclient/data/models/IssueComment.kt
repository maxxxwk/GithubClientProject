package com.pmacademy.githubclient.data.models

import com.google.gson.annotations.SerializedName

data class IssueComment(
    @SerializedName("id") val id: Int,
    @SerializedName("user") val user: User,
    @SerializedName("body") val body: String
)
