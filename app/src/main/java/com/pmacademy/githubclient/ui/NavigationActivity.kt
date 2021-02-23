package com.pmacademy.githubclient.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.pmacademy.githubclient.App
import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.data.GithubAuthService
import com.pmacademy.githubclient.data.models.AccessToken
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
        if (code == null && sharedPref.token == "") {
            navigator.showAuthFragment()
        } else {
            code?.let { code ->
                Thread {
                    getAccessToken(code)?.let { accessToken ->

                        Log.d("LOG_TAG", accessToken.toString())
                        sharedPref.token = "${accessToken.tokenType} ${accessToken.accessToken}"
                        navigator.showUserInfoFragment()
                    }
                }.start()
            }
        }
    }

    private fun getAccessToken(code: String): AccessToken? {
        return githubAuthService.getAccessToken(GithubUtils.clientId, GithubUtils.clientSecret, code)
            .execute()
            .body()
    }
}