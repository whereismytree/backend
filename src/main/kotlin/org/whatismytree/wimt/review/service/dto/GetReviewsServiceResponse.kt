package org.whatismytree.wimt.review.service.dto

import org.whatismytree.wimt.review.repository.dto.ReviewDetailResult
import org.whatismytree.wimt.tree.entity.Tree

data class GetReviewsServiceResponse(
    val reviews: List<ReviewDetailResult>,
    val tree: Tree,
)
