package com.pmacademy.githubclient.data

import okhttp3.Interceptor
import okhttp3.Response

class AcceptHeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.run {
            val newRequest = chain.request().newBuilder()
                .addHeader("Accept", "application/vnd.github.v3+json")
                .build()
            chain.proceed(newRequest)
        }
    }
}
