package org.whatismytree.wimt.review.controller.dto

import jakarta.validation.constraints.Size
import org.whatismytree.wimt.common.constraint.NullOrNotBlank

data class UpdateReviewRequest(
    @field:Size(max = 5)
    val tagIds: List<Long>?,
    @field:NullOrNotBlank
    val content: String?,
    @field:NullOrNotBlank
    val imageUrl: String?,
)
