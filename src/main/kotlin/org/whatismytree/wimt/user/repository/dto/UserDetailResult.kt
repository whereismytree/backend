package org.whatismytree.wimt.user.repository.dto

import org.whatismytree.wimt.auth.domain.OAuthType

data class UserDetailResult(
    val nickname: String?,
    val email: String,
    val oauthType: OAuthType,
    val profileImageUrl: String?,
    val postedTreesCount: Long,
    val savedTreesCount: Long,
    val reviewsCount: Long,
)
