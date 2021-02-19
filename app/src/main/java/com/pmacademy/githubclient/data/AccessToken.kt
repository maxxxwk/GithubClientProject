package com.pmacademy.githubclient.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccessToken(
    @SerialName("access_token") val accessToken: String,
    @SerialName("scope") val scope: String,
    @SerialName("token_type") val tokenType: String,
)
