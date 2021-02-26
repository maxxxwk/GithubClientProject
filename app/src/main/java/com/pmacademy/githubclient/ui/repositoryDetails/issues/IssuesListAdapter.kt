package com.pmacademy.githubclient.ui.repositoryDetails.issues

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pmacademy.githubclient.data.models.Issue
import com.pmacademy.githubclient.data.models.Repository
import com.pmacademy.githubclient.databinding.IssuesListItemBinding

class IssuesListAdapter(
    private val onClickItemCallback: (issue: Issue) -> Unit
) :
    ListAdapter<Issue, IssuesListAdapter.IssueViewHolder>(IssueDiffCallback()) {

    class IssueViewHolder(
        private val binding: IssuesListItemBinding,
        private val onClickItemCallback: (issue: Issue) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(issue: Issue) {
            with(binding) {
                tvIssueName.text = issue.title
                root.setOnClickListener {
                    onClickItemCallback(issue)
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
        return IssueViewHolder(binding, onClickItemCallback)
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}