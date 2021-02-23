package com.pmacademy.githubclient.utils

import android.net.Uri

object GithubUtils {
    const val clientId = "Iv1.e42a61ea14b6b8ac"
    const val clientSecret = "1f7fb0a17bba8a80d04da59321696d443f3292ec"
    const val redirectUrl = "githubclientproject://callback"
    const val scopes = "repo, user"
    const val schema = "https"
    const val githubHost = "github.com"

    fun buildAuthGitHubUrl(): Uri {
        return Uri.Builder()
            .scheme(schema)
            .authority(githubHost)
            .appendEncodedPath("login/oauth/authorize")
            .appendQueryParameter("client_id", clientId)
            .appendQueryParameter("scope", scopes)
            .appendQueryParameter("redirect_url", redirectUrl)
            .build()
    }

    fun getCodeFromUri(uri: Uri?): String? {
        uri ?: return null
        if (!uri.toString().startsWith(redirectUrl)) {
            return null
        }
        return uri.getQueryParameter("code")
    }
}