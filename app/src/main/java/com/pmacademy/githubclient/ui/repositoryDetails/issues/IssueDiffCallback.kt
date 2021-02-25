package com.pmacademy.githubclient.ui.repositoryDetails.issues

import androidx.recyclerview.widget.DiffUtil
import com.pmacademy.githubclient.data.models.Issue

class IssueDiffCallback : DiffUtil.ItemCallback<Issue>() {
    override fun areItemsTheSame(oldItem: Issue, newItem: Issue): Boolean {
        return oldItem.number == newItem.number
    }

    override fun areContentsTheSame(oldItem: Issue, newItem: Issue): Boolean {
        return oldItem == newItem
    }
}