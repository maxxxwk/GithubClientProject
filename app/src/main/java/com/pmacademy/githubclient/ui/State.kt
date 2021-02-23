package com.pmacademy.githubclient.ui

sealed class State<out DATA, out ERROR> {
    data class Content<DATA>(val data: DATA) : State<DATA, Nothing>()
    data class Error<ERROR>(val error: ERROR) : State<Nothing, ERROR>()
    object Loading : State<Nothing, Nothing>()
}
