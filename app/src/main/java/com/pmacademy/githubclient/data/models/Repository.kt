package com.pmacademy.githubclient.data.models

import com.google.gson.annotations.SerializedName

data class Repository(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,

) {
    override fun toString(): String {
        return if (description.isNullOrEmpty()) {
            "Repository(name='$name)"
        } else {
            "Repository(name='$name', description='$description')"
        }
    }
}
