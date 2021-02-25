package com.pmacademy.githubclient.ui.projectDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.pmacademy.githubclient.data.models.Issue
import com.pmacademy.githubclient.databinding.IssueDetailsFragmentBinding
import com.pmacademy.githubclient.databinding.IssueItemBinding
import com.pmacademy.githubclient.databinding.ProjectFragmentBinding

class ProjectIssueListAdapter
    :ListAdapter<Issue, ProjectIssueListAdapter.ProjectIssue>(ProjectIssueCallBack()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProjectIssue {

        val binding = IssueItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProjectIssue(binding)

    }

    override fun onBindViewHolder(holder: ProjectIssue, position: Int) {
        holder.bind(getItem(position))
    }

    class ProjectIssue(private val binding: IssueItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(issue: Issue) {
            with(binding) {
                tvIssueTitle.text = issue.title
                tvIssueContent.text = issue.body
            }
        }
    }
}