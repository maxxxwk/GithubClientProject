package com.pmacademy.githubclient.ui

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment(@LayoutRes layout: Int) : Fragment(layout) {
    protected val navigator: Navigator by lazy {
        (requireActivity() as NavigationActivity).navigator
    }
}