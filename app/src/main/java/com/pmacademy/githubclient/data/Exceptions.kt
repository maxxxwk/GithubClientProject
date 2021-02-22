package com.pmacademy.githubclient.data


sealed class Exceptions<String> {
    data class Success(val value: String) : Exceptions<String>()
    data class NotFoundError(val value: String) : Exceptions<String>()
    data class PaymentRequired(val value: String) : Exceptions<String>()
    data class ForbiddenError(val value: String) : Exceptions<String>()
    data class Unauthorized(val value: String) : Exceptions<String>()
}