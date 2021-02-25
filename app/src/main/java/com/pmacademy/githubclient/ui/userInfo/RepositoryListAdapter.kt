package com.pmacademy.githubclient.ui.userInfo

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pmacademy.githubclient.data.models.Repository
import com.pmacademy.githubclient.databinding.RepositoryItemBinding

class RepositoryListAdapter :
    ListAdapter<Repository, RepositoryListAdapter.RepositoryViewHolder>(
        RepositoryDiffCallback()
    ) {
    private val user:UserInfo? = null
    class RepositoryViewHolder(private val binding: RepositoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(repositoryUIModel: Repository) {
            with(binding) {
                tvRepositoryName.text = repositoryUIModel.name
                tvRepositoryDescription.text = repositoryUIModel.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val binding = RepositoryItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return RepositoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
//            inject
        }
    }
}