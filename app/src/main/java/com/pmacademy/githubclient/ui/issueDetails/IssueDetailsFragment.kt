package com.pmacademy.githubclient.ui.issueDetails

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.pmacademy.githubclient.App
import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.databinding.IssueDetailsFragmentBinding
import com.pmacademy.githubclient.ui.BaseFragment
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
        private const val REPOS_KEY = "REPOS_KEY"
        private const val OWNER_KEY = "OWNER_KEY"

        fun newInstance(repos: String, owner: String, number: Int): IssueDetailsFragment {
            return IssueDetailsFragment().also {
                it.arguments = Bundle().apply {
                    putInt(ISSUE_NUMBER_KEY, number)
                    putString(REPOS_KEY, repos)
                    putString(OWNER_KEY, owner)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = IssueDetailsFragmentBinding.bind(view)
        setupRecyclerView()
        ((requireActivity() as NavigationActivity).application as App).daggerComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[IssueDetailsViewModel::class.java]
        observeViewModel()
        loadIssueDetails()
    }

    private fun loadIssueDetails() {
        requireArguments().let {
            val number = it.getInt(ISSUE_NUMBER_KEY, 0)
            val repos = it.getString(REPOS_KEY, "")
            val owner = it.getString(OWNER_KEY, "")
            viewModel.loadIssueDetails(repos, owner, number)
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