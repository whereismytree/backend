package org.whatismytree.wimt.review.controller.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.whatismytree.wimt.common.constraint.NullOrNotBlank

data class CreateReviewRequest(
    @field:Min(1)
    val treeId: Long,
    @field:Size(max = 5)
    val tagIds: List<Long>,
    @field:NotBlank
    val content: String,
    @field:NullOrNotBlank
    val imageUrl: String?,
)
