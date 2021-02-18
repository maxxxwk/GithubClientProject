package com.pmacademy.githubclient.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pmacademy.githubclient.R
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
    }

    override fun onResume() {
        super.onResume()
        navigator.showAuthFragment()
    }
}