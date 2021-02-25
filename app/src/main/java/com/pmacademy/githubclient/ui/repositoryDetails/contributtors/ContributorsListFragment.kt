package com.pmacademy.githubclient.ui.repositoryDetails.contributtors

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pmacademy.githubclient.App
import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.data.models.User
import com.pmacademy.githubclient.databinding.ContributorsListFragmentBinding
import com.pmacademy.githubclient.ui.BaseFragment
import com.pmacademy.githubclient.ui.Error
import com.pmacademy.githubclient.ui.NavigationActivity
import com.pmacademy.githubclient.ui.State
import com.pmacademy.githubclient.ui.repositoryDetails.issues.IssuesListFragment
import com.pmacademy.githubclient.ui.repositoryDetails.issues.IssuesListViewModel
import com.pmacademy.githubclient.utils.viewModelFactory.ViewModelFactory
import javax.inject.Inject

class ContributorsListFragment : BaseFragment(R.layout.contributors_list_fragment) {

    private lateinit var binding: ContributorsListFragmentBinding
    private lateinit var viewModel: ContributorsListViewModel
    private val contributorsListAdapter = ContributorsListAdapter{
        navigator.showUserInfoFragment(it)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    companion object {
        private const val REPOS_KEY = "REPOS_KEY"
        private const val OWNER_KEY = "OWNER_KEY"

        fun newInstance(reposName: String, reposOwner: String): ContributorsListFragment {
            return ContributorsListFragment().also {
                it.arguments = Bundle().apply {
                    putString(REPOS_KEY, reposName)
                    putString(OWNER_KEY, reposOwner)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ContributorsListFragmentBinding.bind(view)
        setupRecyclerView()
        ((requireActivity() as NavigationActivity).application as App).daggerComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[ContributorsListViewModel::class.java]
        observeViewModel()
        loadContributors()
    }

    private fun loadContributors() {
        with(requireArguments()) {
            val repos = getString(REPOS_KEY, "")
            val owner = getString(OWNER_KEY, "")
            viewModel.loadContributors(repos, owner)
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvContributors) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = contributorsListAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.stateLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is State.Loading -> showLoading()
                is State.Content -> showContributors(it.data)
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

    private fun showContributors(contributors: List<User>) {
        hideLoading()
        contributorsListAdapter.submitList(contributors)
    }

    private fun hideLoading() {
        with(binding) {
            pbLoading.visibility = View.GONE
            rvContributors.visibility = View.VISIBLE
        }
    }

    private fun showLoading() {
        with(binding) {
            pbLoading.visibility = View.VISIBLE
            rvContributors.visibility = View.GONE
        }
    }
}