package com.pmacademy.githubclient.ui.repositoryDetails

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragment: Fragment, private val content: List<Fragment>) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = content.size

    override fun createFragment(position: Int): Fragment = content[position]
}