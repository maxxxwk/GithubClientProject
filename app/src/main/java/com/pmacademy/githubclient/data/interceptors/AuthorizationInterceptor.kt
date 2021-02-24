package com.pmacademy.githubclient.data.interceptors

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.run {
            val newRequest = chain.request().newBuilder()
                .addHeader("Accept", "application/vnd.github.v3+json")
                .build()
            chain.proceed(newRequest)
        }
    }
}
