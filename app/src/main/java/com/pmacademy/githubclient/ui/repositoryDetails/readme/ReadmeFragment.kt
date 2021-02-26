package com.pmacademy.githubclient.ui.repositoryDetails.readme

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.pmacademy.githubclient.App
import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.databinding.ReadmeFragmentBinding
import com.pmacademy.githubclient.ui.BaseFragment
import com.pmacademy.githubclient.ui.Error
import com.pmacademy.githubclient.ui.NavigationActivity
import com.pmacademy.githubclient.ui.State
import javax.inject.Inject

class ReadmeFragment : BaseFragment(R.layout.readme_fragment) {

    private lateinit var binding: ReadmeFragmentBinding
    private lateinit var viewModel: ReadmeViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    companion object {
        private const val REPOS_NAME_KEY = "REPOS_NAME_KEY"
        private const val OWNER_NAME_KEY = "OWNER_NAME_KEY"

        fun newInstance(reposName: String, reposOwner: String): ReadmeFragment {
            return ReadmeFragment().also {
                it.arguments = Bundle().apply {
                    putString(REPOS_NAME_KEY, reposName)
                    putString(OWNER_NAME_KEY, reposOwner)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ReadmeFragmentBinding.bind(view)
        initViewModel()
        observeViewModel()
        loadReadme()
    }

    private fun initViewModel() {
        ((requireActivity() as NavigationActivity).application as App).daggerComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[ReadmeViewModel::class.java]
    }

    private fun loadReadme() {
        with(requireArguments()) {
            val reposName = getString(REPOS_NAME_KEY, "")
            val ownerName = getString(OWNER_NAME_KEY, "")
            viewModel.loadReadme(reposName, ownerName)
        }
    }

    private fun observeViewModel() {
        viewModel.stateLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is State.Loading -> showLoading()
                is State.Content -> showReadme(it.data)
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

    private fun showReadme(readme: String) {
        hideLoading()
        binding.tvReadme.text = readme
    }

    private fun showLoading() {
        with(binding) {
            pbLoading.visibility = View.VISIBLE
            svReadme.visibility = View.GONE
        }
    }

    private fun hideLoading() {
        with(binding) {
            pbLoading.visibility = View.GONE
            svReadme.visibility = View.VISIBLE
        }
    }
}