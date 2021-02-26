package com.pmacademy.githubclient.ui.repositoryDetails.issues

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pmacademy.githubclient.App
import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.data.models.Issue
import com.pmacademy.githubclient.databinding.IssuesListFragmentBinding
import com.pmacademy.githubclient.ui.BaseFragment
import com.pmacademy.githubclient.ui.Error
import com.pmacademy.githubclient.ui.NavigationActivity
import com.pmacademy.githubclient.ui.State
import javax.inject.Inject

class IssuesListFragment : BaseFragment(R.layout.issues_list_fragment) {
    private lateinit var binding: IssuesListFragmentBinding
    private lateinit var viewModel: IssuesListViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    companion object {
        private const val REPOS_NAME_KEY = "REPOS_NAME_KEY"
        private const val OWNER_NAME_KEY = "OWNER_NAME_KEY"

        fun newInstance(reposName: String, reposOwner: String): IssuesListFragment {
            return IssuesListFragment().also {
                it.arguments = Bundle().apply {
                    putString(REPOS_NAME_KEY, reposName)
                    putString(OWNER_NAME_KEY, reposOwner)
                }
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = IssuesListFragmentBinding.bind(view)
        setupRecyclerView()
        initViewModel()
        observeViewModel()
        loadIssues()
    }

    private fun initViewModel() {
        ((requireActivity() as NavigationActivity).application as App).daggerComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[IssuesListViewModel::class.java]
    }

    private fun loadIssues() {
        with(requireArguments()) {
            val reposName = getString(REPOS_NAME_KEY, "")
            val ownerName = getString(OWNER_NAME_KEY, "")
            viewModel.loadIssues(reposName, ownerName)
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvIssues) {
            layoutManager = LinearLayoutManager(requireContext())
            with(requireArguments()) {
                val reposName = getString(REPOS_NAME_KEY, "")
                val ownerName = getString(OWNER_NAME_KEY, "")
                adapter = IssuesListAdapter { issue ->
                    navigator.showIssueDetailsFragment(issue, reposName, ownerName)
                }
            }
        }
    }

    private fun observeViewModel() {
        viewModel.stateLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is State.Loading -> showLoading()
                is State.Content -> showIssues(it.data)
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

    private fun showIssues(issues: List<Issue>) {
        hideLoading()
        (binding.rvIssues.adapter as IssuesListAdapter).submitList(issues)
        if (issues.isEmpty()) {
            binding.tvNoIssuesMessage.visibility = View.VISIBLE
        } else {
            binding.tvNoIssuesMessage.visibility = View.GONE
        }
    }

    private fun hideLoading() {
        with(binding) {
            pbLoading.visibility = View.GONE
            rvIssues.visibility = View.VISIBLE
        }
    }

    private fun showLoading() {
        with(binding) {
            pbLoading.visibility = View.VISIBLE
            rvIssues.visibility = View.GONE
        }
    }
}