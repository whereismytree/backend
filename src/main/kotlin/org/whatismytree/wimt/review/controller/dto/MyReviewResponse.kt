package org.whatismytree.wimt.review.controller.dto

import org.whatismytree.wimt.review.repository.dto.MyReviewResult
import org.whatismytree.wimt.tree.controller.dto.TreeSummary
import java.time.LocalDateTime

data class MyReviewResponse(
    val reviews: List<MyReviews>,
) {

    val totalReviews: Int
        get() = reviews.size

    data class MyReviews(
        val reviewId: Long,
        val tree: TreeSummary,
        val createdAt: LocalDateTime,
        val reviewImageUrl: String?,
        val content: String,
        val tags: List<String>,
    )

    companion object {
        fun of(reviews: List<MyReviewResult>): MyReviewResponse {
            return MyReviewResponse(
                reviews = reviews.map {
                    MyReviews(
                        reviewId = it.reviewId,
                        tree = TreeSummary(
                            treeId = it.treeId,
                            treeName = it.treeName,
                            address = it.address,
                        ),
                        createdAt = it.createdAt,
                        content = it.content,
                        reviewImageUrl = it.reviewImageUrl,
                        tags = it.tags,
                    )
                },
            )
        }
    }
}
