package com.pmacademy.githubclient.data.models

import com.google.gson.annotations.SerializedName

data class Repository(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
)