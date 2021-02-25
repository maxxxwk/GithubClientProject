package com.pmacademy.githubclient.ui.issueDetails

import com.pmacademy.githubclient.data.models.Issue
import com.pmacademy.githubclient.data.models.IssueComment

data class IssueDetails(
    val issue: Issue,
    val comments: List<IssueComment>
)
