package com.pmacademy.githubclient.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pmacademy.githubclient.R

class NavigationActivity : AppCompatActivity() {

    val navigator: Navigator by lazy {
        Navigator(supportFragmentManager, R.id.container)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
    }

    override fun onResume() {
        super.onResume()
        navigator.showAuthFragment()
    }
}