package com.pmacademy.githubclient.ui.userInfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pmacademy.githubclient.data.models.Repository
import com.pmacademy.githubclient.databinding.RepositoryItemBinding

class RepositoryListAdapter(private val callback: (Repository) -> Unit) :
    ListAdapter<Repository, RepositoryListAdapter.RepositoryViewHolder>(
        RepositoryDiffCallback()
    ) {
    class RepositoryViewHolder(
        private val binding: RepositoryItemBinding,
        private val callback: (Repository) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(repository: Repository) {
            with(binding) {
                tvRepositoryName.text = repository.name
                tvRepositoryDescription.text = repository.description
                root.setOnClickListener {
                    callback(repository)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val binding = RepositoryItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return RepositoryViewHolder(binding, callback)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}