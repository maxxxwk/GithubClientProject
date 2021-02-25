package com.pmacademy.githubclient.ui.repositoryDetails.issues

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pmacademy.githubclient.data.models.Issue
import com.pmacademy.githubclient.data.models.Repository
import com.pmacademy.githubclient.databinding.IssuesListItemBinding

class IssuesListAdapter(
    private val repos: String,
    private val owner: String,
    private val callback: (issue: Issue) -> Unit
) :
    ListAdapter<Issue, IssuesListAdapter.IssueViewHolder>(IssueDiffCallback()) {

    class IssueViewHolder(
        private val binding: IssuesListItemBinding,
        private val callback: (issue: Issue) -> Unit,
        private val repos: String,
        private val owner: String
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(issue: Issue) {
            with(binding) {
                tvIssueName.text = issue.title
                root.setOnClickListener {
                    callback(issue)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        val binding = IssuesListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IssueViewHolder(binding, callback, repos, owner)
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}