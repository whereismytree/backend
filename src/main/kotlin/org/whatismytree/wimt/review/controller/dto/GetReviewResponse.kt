package org.whatismytree.wimt.review.controller.dto

import org.whatismytree.wimt.review.repository.dto.ReviewDetailResult

data class GetReviewResponse(
    val nickname: String?,
    val profileImageUrl: String?,
    val createdAt: String,
    val reviewImageUrl: String?,
    val content: String,
    val tags: List<String>,
    val canEdit: Boolean,
    val canRemove: Boolean,
) {
    companion object {
        fun of(
            reviewDetailResult: ReviewDetailResult,
            canEdit: Boolean,
            canRemove: Boolean,
        ): GetReviewResponse {
            return GetReviewResponse(
                nickname = reviewDetailResult.authorNickname,
                profileImageUrl = reviewDetailResult.authorProfileUrl,
                createdAt = reviewDetailResult.createdAt.toString(),
                reviewImageUrl = reviewDetailResult.imageUrl,
                content = reviewDetailResult.content,
                tags = reviewDetailResult.tags,
                canEdit = canEdit,
                canRemove = canRemove,
            )
        }
    }
}
