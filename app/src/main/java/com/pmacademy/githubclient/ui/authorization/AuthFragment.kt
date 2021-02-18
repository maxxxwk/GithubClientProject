package com.pmacademy.githubclient.ui.authorization

import android.os.Bundle
import android.view.View
import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.ui.BaseFragment

class AuthFragment private constructor(): BaseFragment(R.layout.authorization_fragment) {

    companion object {
        fun newInstance(): AuthFragment {
            return AuthFragment()
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}