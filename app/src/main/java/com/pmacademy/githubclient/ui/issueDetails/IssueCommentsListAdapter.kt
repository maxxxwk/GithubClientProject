package com.pmacademy.githubclient.ui.issueDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.data.models.IssueComment
import com.pmacademy.githubclient.databinding.IssueCommentItemBinding

class IssueCommentsListAdapter :
    ListAdapter<IssueComment, IssueCommentsListAdapter.IssueCommentViewHolder>(
        IssueCommentDiffCallback()
    ) {

    class IssueCommentViewHolder(private val binding: IssueCommentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(issueComment: IssueComment) {
            with(binding) {
                Glide.with(binding.root.context)
                    .load(issueComment.user.avatar_url)
                    .placeholder(R.drawable.loading_placeholder)
                    .into(ivCommentatorAvatar)
                tvCommentatorName.text = issueComment.user.login
                tvCommentBody.text = issueComment.body
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueCommentViewHolder {
        val binding = IssueCommentItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IssueCommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IssueCommentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}