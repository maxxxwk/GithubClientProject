package com.pmacademy.githubclient.ui.issueDetails

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.pmacademy.githubclient.App
import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.data.models.Issue
import com.pmacademy.githubclient.databinding.IssueDetailsFragmentBinding
import com.pmacademy.githubclient.ui.BaseFragment
import com.pmacademy.githubclient.ui.Error
import com.pmacademy.githubclient.ui.NavigationActivity
import com.pmacademy.githubclient.ui.State
import javax.inject.Inject

class IssueDetailsFragment : BaseFragment(R.layout.issue_details_fragment) {

    private lateinit var binding: IssueDetailsFragmentBinding
    private val issueCommentsListAdapter = IssueCommentsListAdapter()
    private lateinit var viewModel: IssueDetailsViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    companion object {
        private const val ISSUE_NUMBER_KEY = "ISSUE_NUMBER_KEY"
        private const val REPOS_NAME_KEY = "REPOS_NAME_KEY"
        private const val OWNER_NAME_KEY = "OWNER_NAME_KEY"

        fun newInstance(issue: Issue, reposName: String, reposOwner: String): IssueDetailsFragment {
            return IssueDetailsFragment().also {
                it.arguments = Bundle().apply {
                    putInt(ISSUE_NUMBER_KEY, issue.number)
                    putString(REPOS_NAME_KEY, reposName)
                    putString(OWNER_NAME_KEY, reposOwner)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = IssueDetailsFragmentBinding.bind(view)
        setupRecyclerView()
        initViewModel()
        observeViewModel()
        loadIssueDetails()
    }

    private fun initViewModel() {
        ((requireActivity() as NavigationActivity).application as App).daggerComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[IssueDetailsViewModel::class.java]
    }

    private fun loadIssueDetails() {
        requireArguments().let {
            val issueNumber = it.getInt(ISSUE_NUMBER_KEY, 0)
            val reposName = it.getString(REPOS_NAME_KEY, "")
            val ownerName = it.getString(OWNER_NAME_KEY, "")
            viewModel.loadIssueDetails(reposName, ownerName, issueNumber)
        }
    }

    private fun observeViewModel() {
        viewModel.stateLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is State.Loading -> showLoading()
                is State.Content -> showIssueDetails(it.data)
                is State.Error -> {
                    when (it.error) {
                        Error.UNAUTHORIZED_ERROR -> {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.unauthorized_error_message),
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                        Error.NOT_FOUND_ERROR -> {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.not_found_error_message),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        Error.FORBIDDEN_ERROR -> {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.forbidden_error_message),
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                        Error.LOADING_ERROR -> {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.loading_error_message),
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    }
                }
            }
        })
    }

    private fun setupRecyclerView() {
        with(binding.rvComments) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = issueCommentsListAdapter
        }
    }

    private fun showLoading() {
        with(binding) {
            issueDetailsGroup.visibility = View.GONE
            pbLoading.visibility = View.VISIBLE
        }
    }

    private fun hideLoading() {
        with(binding) {
            issueDetailsGroup.visibility = View.VISIBLE
            pbLoading.visibility = View.GONE
        }
    }

    private fun showIssueDetails(issueDetails: IssueDetails) {
        hideLoading()
        with(binding) {
            Glide.with(requireContext())
                .load(issueDetails.issue.user.avatar_url)
                .placeholder(R.drawable.loading_placeholder)
                .into(ivAuthorAvatar)
            tvAuthor.text = issueDetails.issue.user.login
            tvIssueTitle.text = issueDetails.issue.title
            tvIssueContent.text = issueDetails.issue.body
        }
        issueCommentsListAdapter.submitList(issueDetails.comments)
    }
}