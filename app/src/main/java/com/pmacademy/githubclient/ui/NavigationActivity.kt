package com.pmacademy.githubclient.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pmacademy.githubclient.databinding.ActivityNavigationBinding

class NavigationActivity : AppCompatActivity() {
    private lateinit var binding:ActivityNavigationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationBinding.inflate(layoutInflater)

    }
}