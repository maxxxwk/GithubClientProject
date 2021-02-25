package com.pmacademy.githubclient.ui.issueDetails

import androidx.recyclerview.widget.DiffUtil
import com.pmacademy.githubclient.data.models.IssueComment

class IssueCommentDiffCallback : DiffUtil.ItemCallback<IssueComment>() {
    override fun areItemsTheSame(oldItem: IssueComment, newItem: IssueComment): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: IssueComment, newItem: IssueComment): Boolean {
        return oldItem == newItem
    }
}