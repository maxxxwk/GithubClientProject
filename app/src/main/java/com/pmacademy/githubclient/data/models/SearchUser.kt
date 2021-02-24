package com.pmacademy.githubclient.data.models

import com.google.gson.annotations.SerializedName

data class SearchUser(
   @SerializedName("item") val item : List<User>
)