package com.pmacademy.githubclient.data.models

import com.google.gson.annotations.SerializedName


data class PullRequest(
   @SerializedName("title")val title: String,
   @SerializedName("id") val id:Int
)