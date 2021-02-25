package com.pmacademy.githubclient.data.models

import com.google.gson.annotations.SerializedName

data class Readme(
    @SerializedName("content") val content: String
)
