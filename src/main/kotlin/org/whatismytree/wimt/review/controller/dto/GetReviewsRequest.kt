package org.whatismytree.wimt.review.controller.dto

import jakarta.validation.constraints.Min

data class GetReviewsRequest(
    @field:Min(1)
    val treeId: Long,
)
