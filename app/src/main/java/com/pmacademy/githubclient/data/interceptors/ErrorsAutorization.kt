package com.pmacademy.githubclient.data.interceptors

import com.pmacademy.githubclient.data.Exceptions
import okhttp3.Interceptor
import okhttp3.Response

class ErrorsAutorization : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        when (response.code) {
            404 -> Exceptions.NotFoundError("Not Found")
            403 -> Exceptions.ForbiddenError("Forbidden Error")
            402 -> Exceptions.PaymentRequired("Payment Required")
            401 -> Exceptions.Unauthorized(" Unauthorized")
            else -> Exceptions.Success("Success")
        }
        return response
    }
}