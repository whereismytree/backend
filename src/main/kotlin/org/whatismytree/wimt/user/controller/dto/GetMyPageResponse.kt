package org.whatismytree.wimt.user.controller.dto

import org.whatismytree.wimt.user.repository.dto.UserDetailResult

data class GetMyPageResponse(
    val nickname: String?,
    val email: String,
    val platform: String,
    val profileImageUrl: String?,
    val postedTreesCount: Long,
    val savedTreesCount: Long,
    val reviewsCount: Long,
) {
    companion object {
        fun of(result: UserDetailResult): GetMyPageResponse {
            return GetMyPageResponse(
                nickname = result.nickname,
                email = result.email,
                platform = result.oauthType.name,
                profileImageUrl = result.profileImageUrl,
                postedTreesCount = result.postedTreesCount,
                savedTreesCount = result.savedTreesCount,
                reviewsCount = result.reviewsCount,
            )
        }
    }
}
