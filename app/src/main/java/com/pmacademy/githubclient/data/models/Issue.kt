package com.pmacademy.githubclient.data.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class Issue(
    @SerializedName("title") val title: String,
    @SerializedName("user") val user: User,
    @SerializedName("number") val number: Int
)