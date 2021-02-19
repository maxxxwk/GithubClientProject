package com.pmacademy.githubclient.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.pmacademy.githubclient.GitHubUtils
import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.data.preferences.SharedPref
import com.pmacademy.githubclient.databinding.ActivityNavigationBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class NavigationActivity : AppCompatActivity() {


    val navigator: Navigator by lazy {
        Navigator(supportFragmentManager, R.id.container)
    }
    private lateinit var binding: ActivityNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
//     create btn and set click
        binding.btn.setOnClickListener {
            startGitHubLogin()
        }
    }

    private val sharedPreferences by lazy {
        SharedPref(this)
    }

    private val githubUtils: GitHubUtils by lazy {
        GitHubUtils()
    }

    private fun startGitHubLogin() {
        val authIntent = Intent(Intent.ACTION_VIEW, githubUtils.buildAuthGitHubUrl())
        startActivity(authIntent)
    }

    override fun onResume() {
        super.onResume()
        navigator.showAuthFragment()
        val code = githubUtils.getCodeFromUri(uri = intent.data)
        code ?: return

        //Get all info about user
        GlobalScope.launch {
            val response = githubUtils.getAccessToken(code)
            val token = "${response.tokenType} ${response.accessToken}"
            sharedPreferences.token = response.tokenType
            val user = githubUtils.getUser(token)
            val repo = githubUtils.getRepo(token)

            Log.d("TAG_11", "user $user $repo $response")
        }
        fun checkTokenAuto(){
            if(sharedPreferences.token.isEmpty()){

            }
            else{

            }
        }

    }
}