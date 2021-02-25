package com.pmacademy.githubclient.ui.projectDetails

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pmacademy.githubclient.App
import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.data.models.Contributors
import com.pmacademy.githubclient.data.models.User
import com.pmacademy.githubclient.databinding.ProjectFragmentBinding
import com.pmacademy.githubclient.databinding.UserInfoFragmentBinding
import com.pmacademy.githubclient.ui.BaseFragment
import com.pmacademy.githubclient.ui.NavigationActivity
import com.pmacademy.githubclient.ui.State
import com.pmacademy.githubclient.ui.issueDetails.Error
import com.pmacademy.githubclient.ui.issueDetails.IssueCommentsListAdapter
import com.pmacademy.githubclient.ui.issueDetails.IssueDetailsViewModel
import com.pmacademy.githubclient.ui.userInfo.RepositoryListAdapter
import com.pmacademy.githubclient.ui.userInfo.UserInfo
import com.pmacademy.githubclient.ui.userInfo.UserInfoFragment
import com.pmacademy.githubclient.ui.userInfo.UserInfoViewModel
import javax.inject.Inject

class ProjectDetailfFragment : BaseFragment(R.layout.project_fragment) {

    private val contributorsListAdapter = ProjectContributorsListAdapter()
    private lateinit var binding: ProjectFragmentBinding
    private lateinit var projectIssue: ProjectIssueListAdapter
    private lateinit var viewModel: ProjectViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    companion object {
        fun newInstance(): ProjectDetailfFragment {
            return ProjectDetailfFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ProjectFragmentBinding.bind(view)
        setupRepositoryRecyclerView()
        ((requireActivity() as NavigationActivity).application as App).daggerComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[ProjectViewModel::class.java]
        observeViewModel()
        viewModel.loadUserInfo("", "")
    }

    private fun setupRepositoryRecyclerView() {
        with(binding.rvContributors) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = contributorsListAdapter
        }
        with(binding.rvIssue) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = projectIssue
        }
    }

    private fun observeViewModel() {
        viewModel.stateLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is State.Loading -> showLoading()
                is State.Content -> showUserContributors(it.data)
                is State.Error -> {
                    when (it.error) {
                        Error.UNAUTHORIZED_ERROR -> {
                            Toast.makeText(requireContext(), "unauthorized", Toast.LENGTH_LONG)
                                .show()
                        }
                        Error.NOT_FOUND_ERROR -> {
                            Toast.makeText(requireContext(), "not found", Toast.LENGTH_LONG).show()
                        }
                        Error.LOADING_ERROR -> {
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

    private fun showUserContributors(contributors: User) {
        hideLoading()
        with(binding) {
            tvUserName.text = contributors.login
        }
    }
}