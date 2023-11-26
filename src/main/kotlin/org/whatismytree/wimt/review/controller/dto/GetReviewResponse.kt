package org.whatismytree.wimt.review.controller.dto

import org.whatismytree.wimt.review.repository.dto.ReviewSummary

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
            reviewSummary: ReviewSummary,
            canEdit: Boolean,
            canRemove: Boolean,
        ): GetReviewResponse {
            return GetReviewResponse(
                nickname = reviewSummary.authorNickname,
                profileImageUrl = reviewSummary.authorProfileUrl,
                createdAt = reviewSummary.createdAt.toString(),
                reviewImageUrl = reviewSummary.imageUrl,
                content = reviewSummary.content,
                tags = reviewSummary.tags,
                canEdit = canEdit,
                canRemove = canRemove,
            )
        }
    }
}
