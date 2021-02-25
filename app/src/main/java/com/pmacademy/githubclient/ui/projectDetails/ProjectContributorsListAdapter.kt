package com.pmacademy.githubclient.ui.projectDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.data.models.User
import com.pmacademy.githubclient.databinding.ContributorsItemBinding
import com.pmacademy.githubclient.ui.userInfo.UserInfo
import com.pmacademy.githubclient.ui.userInfo.UserInfoFragment
import okhttp3.internal.userAgent

class ProjectContributorsListAdapter :
    ListAdapter<User,ProjectContributorsListAdapter.ProjectContributorsViewHolder>(ProjectCallBack()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProjectContributorsViewHolder {

        val binding = ContributorsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProjectContributorsViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ProjectContributorsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ProjectContributorsViewHolder(private val binding:ContributorsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contributors: User) {
            with(binding) {
                Glide.with(binding.root.context)
                    .load(contributors.avatar_url)
                    .placeholder(R.drawable.loading_placeholder)
                    .into(ivContributorsAvatar)
                tvContributorsrName.text = contributors.login
            }
        }
    }
}