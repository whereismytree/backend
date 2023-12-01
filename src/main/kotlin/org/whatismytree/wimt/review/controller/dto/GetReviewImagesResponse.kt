package org.whatismytree.wimt.review.controller.dto

import org.whatismytree.wimt.review.repository.dto.ReviewImageResult

data class GetReviewImagesResponse(
    val images: List<ReviewImage>,
    val totalImages: Long,
) {

    data class ReviewImage(
        val reviewId: Long,
        val imageUrl: String,
    )

    companion object {
        fun of(reviewImageResults: List<ReviewImageResult>): GetReviewImagesResponse {
            return GetReviewImagesResponse(
                images = reviewImageResults.map {
                    ReviewImage(
                        reviewId = it.id,
                        imageUrl = it.imageUrl,
                    )
                },
                totalImages = reviewImageResults.size.toLong(),
            )
        }
    }
}
