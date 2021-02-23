package com.pmacademy.githubclient.ui.userInfo

import androidx.recyclerview.widget.DiffUtil
import com.pmacademy.githubclient.data.models.Repository

class RepositoryDiffCallback : DiffUtil.ItemCallback<Repository>() {
    override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem == newItem
    }

}