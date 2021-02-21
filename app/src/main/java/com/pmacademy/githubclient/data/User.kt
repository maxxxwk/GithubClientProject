package com.pmacademy.githubclient.data

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatar_url: String
)