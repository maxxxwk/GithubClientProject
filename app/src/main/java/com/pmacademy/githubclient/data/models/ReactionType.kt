package com.pmacademy.githubclient.data.models

enum class ReactionType(val content: String) {
    OK("+1"), NOT_OK("-1"), LAUGH("laugh"),
    CONFUSED("confused"), HEART("heart"), HOORAY("hooray"),
    ROCKET("rocket"), EYES("eyes")
}