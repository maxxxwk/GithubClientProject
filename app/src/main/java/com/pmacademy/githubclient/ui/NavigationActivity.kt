package com.pmacademy.githubclient.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pmacademy.githubclient.GitHubUtils
import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.data.preferences.SharedPref
import com.pmacademy.githubclient.databinding.ActivityNavigationBinding

class NavigationActivity : AppCompatActivity() {

    val navigator: Navigator by lazy {
        Navigator(supportFragmentManager, R.id.container)
    }
    private lateinit var binding: ActivityNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    // create btn and set click
//        binding.btn.setOnClickListener {
//            startGitHubLogin()
//        }
    }

    override fun onResume() {
        super.onResume()
        navigator.showAuthFragment()
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
}