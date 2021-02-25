package com.pmacademy.githubclient.ui.userInfo

import com.pmacademy.githubclient.data.models.Repository
import com.pmacademy.githubclient.data.models.User

data class UserInfo(
    val user: User,
    val repositories: List<Repository>

)
