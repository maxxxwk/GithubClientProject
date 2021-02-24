package com.pmacademy.githubclient

import android.app.Application
import com.pmacademy.githubclient.data.interceptors.AuthorizationInterceptor
import com.pmacademy.githubclient.di.AppComponent
import com.pmacademy.githubclient.di.AppModule
import com.pmacademy.githubclient.di.DaggerAppComponent
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

class App: Application() {

    lateinit var daggerComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        daggerComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this)).build()
    }
}