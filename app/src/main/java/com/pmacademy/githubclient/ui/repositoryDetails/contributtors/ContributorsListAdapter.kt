package com.pmacademy.githubclient.ui.repositoryDetails.contributtors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.data.models.User
import com.pmacademy.githubclient.databinding.UsersListItemBinding

class ContributorsListAdapter(
    private val onClickItemCallback: (String) -> Unit
) :
    ListAdapter<User, ContributorsListAdapter.ContributorViewHolder>(ContributorDiffCallback()) {

    class ContributorViewHolder(
        private val binding: UsersListItemBinding,
        private val onClickItemCallback: (String) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contributor: User) {
            with(binding) {
                Glide.with(binding.root.context)
                    .load(contributor.avatar_url)
                    .placeholder(R.drawable.loading_placeholder)
                    .into(ivAvatar)
                tvName.text = contributor.login
                root.setOnClickListener {
                    onClickItemCallback(contributor.login)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContributorViewHolder {
        val binding = UsersListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ContributorViewHolder(binding, onClickItemCallback)
    }

    override fun onBindViewHolder(holder: ContributorViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}