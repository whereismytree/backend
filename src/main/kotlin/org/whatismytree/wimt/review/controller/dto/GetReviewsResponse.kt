package org.whatismytree.wimt.review.controller.dto

import org.whatismytree.wimt.review.service.dto.GetReviewsServiceResponse
import org.whatismytree.wimt.tree.controller.dto.TreeSummary
import java.time.LocalDateTime

data class GetReviewsResponse(
    val reviews: List<Review>,
    val totalReviews: Long,
    val tree: TreeSummary,
) {

    data class Review(
        val reviewId: Long,
        val nickname: String?,
        val profileImageUrl: String?,
        val createdAt: LocalDateTime,
        val reviewImageUrl: String?,
        val content: String,
        val tags: List<String>,
    )

    companion object {
        fun of(serviceResponse: GetReviewsServiceResponse): GetReviewsResponse {
            return GetReviewsResponse(
                reviews = serviceResponse.reviews.map {
                    Review(
                        reviewId = it.id,
                        nickname = it.authorNickname,
                        profileImageUrl = it.authorProfileUrl,
                        createdAt = it.createdAt,
                        reviewImageUrl = it.imageUrl,
                        content = it.content,
                        tags = it.tags,
                    )
                },
                totalReviews = serviceResponse.reviews.size.toLong(),
                tree = TreeSummary.of(serviceResponse.tree),
            )
        }
    }
}
