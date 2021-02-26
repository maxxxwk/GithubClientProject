package com.pmacademy.githubclient.utils

import android.net.Uri

object GithubUtils {
    const val clientId = "Iv1.579fd42febefaf65"
    const val clientSecret = "680cbb713513d806fe83fe305adfb5d00167e674"
    private const val redirectUrl = "githubclientproject://callback"
    private const val scopes = "repo, user"
    private const val schema = "https"
    private const val githubHost = "github.com"

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