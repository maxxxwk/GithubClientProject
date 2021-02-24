package com.pmacademy.githubclient.data.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class PullRequest(
   @SerializedName("title")val title: String,
   @SerializedName("id") val id:Int
)