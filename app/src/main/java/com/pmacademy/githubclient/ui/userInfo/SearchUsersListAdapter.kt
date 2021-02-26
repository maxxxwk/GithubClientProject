package com.pmacademy.githubclient.ui.userInfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.data.models.User
import com.pmacademy.githubclient.databinding.UsersListItemBinding

class SearchUsersListAdapter(
    private val callback: (String) -> Unit
) :
    ListAdapter<User, SearchUsersListAdapter.UserViewHolder>(SearchUsersDiffCallback()) {

    class UserViewHolder(private val binding: UsersListItemBinding,
                         private val callback: (String) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(binding) {
                Glide.with(binding.root.context)
                    .load(user.avatar_url)
                    .placeholder(R.drawable.loading_placeholder)
                    .into(ivAvatar)
                tvName.text = user.login
                root.setOnClickListener {
                    callback(user.login)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UsersListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserViewHolder(binding, callback)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}