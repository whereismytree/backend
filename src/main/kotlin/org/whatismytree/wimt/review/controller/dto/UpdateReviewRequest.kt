package org.whatismytree.wimt.review.controller.dto

import jakarta.validation.constraints.Size

data class UpdateReviewRequest(
    @field:Size(max = 5)
    val tagIds: List<Long>?,
    val content: String?,
    val imageUrl: String?,
)
