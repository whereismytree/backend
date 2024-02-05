package org.whatismytree.wimt.review.repository.dto

import java.time.LocalDateTime

data class MyReviewResult(
    val reviewId: Long,
    val treeName: String,
    val createdAt: LocalDateTime,
    val reviewImageUrl: String,
    val content: String,
    private val stringTags: String?,
) {

    val tags: List<String>
        get() = stringTags?.split(",") ?: listOf()
}
