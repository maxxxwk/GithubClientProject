package com.pmacademy.githubclient.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.pmacademy.githubclient.App
import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.data.GithubAuthService
import com.pmacademy.githubclient.data.models.AuthToken
import com.pmacademy.githubclient.databinding.ActivityNavigationBinding
import com.pmacademy.githubclient.utils.GithubUtils
import com.pmacademy.githubclient.utils.SharedPref
import javax.inject.Inject

class NavigationActivity : AppCompatActivity() {


    val navigator: Navigator by lazy {
        Navigator(supportFragmentManager, R.id.container)
    }
    private lateinit var binding: ActivityNavigationBinding

    @Inject
    lateinit var sharedPref: SharedPref

    @Inject
    lateinit var githubAuthService: GithubAuthService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (application as App).daggerComponent.inject(this)
    }


    override fun onResume() {
        super.onResume()
        val code = GithubUtils.getCodeFromUri(intent.data)
        if (code == null) {
            if (sharedPref.accessToken == "") {
                navigator.showAuthFragment()
            } else {
                navigator.showUserInfoFragment()
            }
        } else {
            Thread {
                getAuthToken(code)?.let { authToken ->
                    sharedPref.accessToken = authToken.accessToken
                    sharedPref.refreshToken = authToken.refreshToken
                    sharedPref.token_type = authToken.tokenType
                    navigator.showUserInfoFragment()
                }
            }.start()
        }
    }

    private fun getAuthToken(code: String): AuthToken? {
        return githubAuthService.getAccessToken(
            GithubUtils.clientId,
            GithubUtils.clientSecret,
            code
        ).execute().body()
    }
}