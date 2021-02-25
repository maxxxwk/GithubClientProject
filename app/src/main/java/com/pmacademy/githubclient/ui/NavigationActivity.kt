package com.pmacademy.githubclient.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pmacademy.githubclient.App
import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.data.GithubAuthService
import com.pmacademy.githubclient.data.models.AuthToken
import com.pmacademy.githubclient.databinding.ActivityNavigationBinding
import com.pmacademy.githubclient.utils.GithubUtils
import com.pmacademy.githubclient.utils.SharedPref
import javax.inject.Inject

class NavigationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavigationBinding
    val navigator: Navigator by lazy {
        Navigator(supportFragmentManager, R.id.container)
    }

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
                navigator.showUserInfoFragment(null)
            }
        } else {
            Thread {
                getAuthToken(code)?.let { authToken ->
                    sharedPref.accessToken = authToken.accessToken
                    sharedPref.tokenType = authToken.tokenType
                    navigator.showUserInfoFragment(null)
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