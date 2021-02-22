package com.pmacademy.githubclient.di

import com.pmacademy.githubclient.data.interceptors.AuthorizationInterceptor
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class AutorizationClass {
    @Provides
    @Singleton
    private fun createAuthorizationInterceptor(retrofit: Retrofit) : AuthorizationInterceptor {
        return  retrofit.create(AuthorizationInterceptor::class.java)
    }
}