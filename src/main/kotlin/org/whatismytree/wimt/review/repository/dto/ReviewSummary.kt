package org.whatismytree.wimt.review.repository.dto

import java.time.LocalDateTime

data class ReviewSummary(
    val id: Long,
    val authorId: Long,
    val authorNickname: String?,
    val authorProfileUrl: String?,
    val createdAt: LocalDateTime,
    val imageUrl: String?,
    val content: String,
    private val stringTags: String,
) {
    val tags: List<String>
        get() = stringTags.split(",")
}
