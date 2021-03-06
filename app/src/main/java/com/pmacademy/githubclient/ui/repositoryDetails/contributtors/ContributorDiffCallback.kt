package com.pmacademy.githubclient.ui.repositoryDetails.contributtors

import androidx.recyclerview.widget.DiffUtil
import com.pmacademy.githubclient.data.models.User

class ContributorDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.login == newItem.login
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}