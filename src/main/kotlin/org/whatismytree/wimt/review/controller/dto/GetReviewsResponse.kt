package org.whatismytree.wimt.review.controller.dto

import org.whatismytree.wimt.review.repository.dto.ReviewDetailResult
import java.time.LocalDateTime

data class GetReviewsResponse(
    val reviews: List<Review>,
    val totalReviews: Long,
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
        fun of(reviewResult: List<ReviewDetailResult>): GetReviewsResponse {
            return GetReviewsResponse(
                reviews = reviewResult.map {
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
                totalReviews = reviewResult.size.toLong(),
            )
        }
    }
}
