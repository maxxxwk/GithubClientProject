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
        private const val REPOS_KEY = "REPOS_KEY"
        private const val OWNER_KEY = "OWNER_KEY"

        fun newInstance(reposName: String, reposOwner: String): IssuesListFragment {
            return IssuesListFragment().also {
                it.arguments = Bundle().apply {
                    putString(REPOS_KEY, reposName)
                    putString(OWNER_KEY, reposOwner)
                }
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = IssuesListFragmentBinding.bind(view)
        setupRecyclerView()
        ((requireActivity() as NavigationActivity).application as App).daggerComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[IssuesListViewModel::class.java]
        observeViewModel()
        loadIssues()
    }

    private fun loadIssues() {
        with(requireArguments()) {
            val repos = getString(REPOS_KEY, "")
            val owner = getString(OWNER_KEY, "")
            viewModel.loadIssues(repos, owner)
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvIssues) {
            layoutManager = LinearLayoutManager(requireContext())
            with(requireArguments()) {
                val repos = getString(REPOS_KEY, "")
                val owner = getString(OWNER_KEY, "")
                adapter = IssuesListAdapter(repos, owner) { issue ->
                    navigator.showIssueDetailsFragment(issue, repos, owner)
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
                            Toast.makeText(requireContext(), "unauthorized", Toast.LENGTH_LONG)
                                .show()
                        }
                        Error.NOT_FOUND_ERROR -> {
                            Toast.makeText(requireContext(), "not found", Toast.LENGTH_LONG).show()
                        }
                        Error.FORBIDDEN_ERROR -> {
                            Toast.makeText(requireContext(), "forbidden error", Toast.LENGTH_LONG)
                                .show()
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