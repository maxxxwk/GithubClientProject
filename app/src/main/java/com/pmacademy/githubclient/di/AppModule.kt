package com.pmacademy.githubclient.di

import android.content.Context
import com.pmacademy.githubclient.data.GithubAuthService
import com.pmacademy.githubclient.data.GithubDataService
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    companion object {
        private const val AUTH_BASE_URL = "https://github.com"
        private const val DATA_BASE_URL = "https://api.github.com"
    }

    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideGithubDataService(gsonConverterFactory: GsonConverterFactory): GithubDataService {
        return Retrofit.Builder().baseUrl(DATA_BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(GithubDataService::class.java)
    }
    @Provides
    @Singleton
    fun provideGithubAuthService(gsonConverterFactory: GsonConverterFactory): GithubAuthService {
        return Retrofit.Builder().baseUrl(AUTH_BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(GithubAuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

}