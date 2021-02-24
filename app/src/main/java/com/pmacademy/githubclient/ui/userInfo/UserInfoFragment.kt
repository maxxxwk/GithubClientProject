package com.pmacademy.githubclient.ui.userInfo

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.pmacademy.githubclient.App
import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.databinding.UserInfoFragmentBinding
import com.pmacademy.githubclient.ui.BaseFragment
import com.pmacademy.githubclient.ui.NavigationActivity
import com.pmacademy.githubclient.ui.State
import com.pmacademy.githubclient.utils.SharedPref
import javax.inject.Inject

class UserInfoFragment private constructor() : BaseFragment(R.layout.user_info_fragment) {

    private lateinit var binding: UserInfoFragmentBinding

    @Inject
    lateinit var viewModel: UserInfoViewModel
    private val repositoryListAdapter = RepositoryListAdapter()

    companion object {
        fun newInstance(): UserInfoFragment {
            return UserInfoFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = UserInfoFragmentBinding.bind(view)
        setupRepositoryRecyclerView()
        ((requireActivity() as NavigationActivity).application as App).daggerComponent.inject(this)
        observeViewModel()
        viewModel.loadUserInfo()
    }

    private fun setupRepositoryRecyclerView() {
        with(binding.rvRepositories) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = repositoryListAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.userInfoStateLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is State.Loading -> showLoading()
                is State.Content -> showUserInfo(it.data)
                is State.Error -> {
                    when (it.error) {
                        UserInfoError.UNAUTHORIZED_ERROR -> {
                            Toast.makeText(requireContext(), "unauthorized", Toast.LENGTH_LONG)
                                .show()
                        }
                        UserInfoError.NOT_FOUND_ERROR -> {
                            Toast.makeText(requireContext(), "not found", Toast.LENGTH_LONG).show()
                        }
                        UserInfoError.LOADING_ERROR -> {
                            Toast.makeText(requireContext(), "loading error", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        })
    }

    private fun showLoading() {
        with(binding) {
            userInfoGroup.visibility = View.GONE
            pbLoading.visibility = View.VISIBLE
        }
    }

    private fun hideLoading() {
        with(binding) {
            userInfoGroup.visibility = View.VISIBLE
            pbLoading.visibility = View.GONE
        }
    }

    private fun showUserInfo(userInfo: UserInfo) {
        hideLoading()
        with(binding) {
            tvUserName.text = userInfo.user.login
            Glide.with(requireContext())
                .load(userInfo.user.avatar_url)
                .placeholder(R.drawable.loading_placeholder)
                .into(ivAvatar)
            repositoryListAdapter.submitList(userInfo.repositories)
        }
    }
}