package com.pmacademy.githubclient.ui.repositoryDetails.contributtors

import android.os.Bundle
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
import com.pmacademy.githubclient.utils.viewModelFactory.ViewModelFactory
import javax.inject.Inject

class ContributorsListFragment : BaseFragment(R.layout.contributors_list_fragment) {

    private lateinit var binding: ContributorsListFragmentBinding
    private lateinit var viewModel: ContributorsListViewModel
    private val contributorsListAdapter = ContributorsListAdapter {
        navigator.showUserInfoFragment(it)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    companion object {
        private const val REPOS_NAME_KEY = "REPOS_NAME_KEY"
        private const val OWNER_NAME_KEY = "OWNER_NAME_KEY"

        fun newInstance(reposName: String, reposOwner: String): ContributorsListFragment {
            return ContributorsListFragment().also {
                it.arguments = Bundle().apply {
                    putString(REPOS_NAME_KEY, reposName)
                    putString(OWNER_NAME_KEY, reposOwner)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ContributorsListFragmentBinding.bind(view)
        setupRecyclerView()
        initViewModel()
        observeViewModel()
        loadContributors()
    }

    private fun initViewModel() {
        ((requireActivity() as NavigationActivity).application as App).daggerComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[ContributorsListViewModel::class.java]
    }

    private fun loadContributors() {
        with(requireArguments()) {
            val reposName = getString(REPOS_NAME_KEY, "")
            val ownerName = getString(OWNER_NAME_KEY, "")
            viewModel.loadContributors(reposName, ownerName)
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