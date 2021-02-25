package com.pmacademy.githubclient.ui.projectDetails

import androidx.recyclerview.widget.DiffUtil
import com.pmacademy.githubclient.data.models.Issue

class ProjectIssueCallBack : DiffUtil.ItemCallback<Issue>() {
    override fun areItemsTheSame(oldItem: Issue, newItem: Issue): Boolean {
        return oldItem.body == newItem.body
    }

    override fun areContentsTheSame(oldItem: Issue, newItem: Issue): Boolean {
        return oldItem == newItem
    }
}