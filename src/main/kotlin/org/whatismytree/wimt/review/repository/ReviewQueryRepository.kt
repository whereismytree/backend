package org.whatismytree.wimt.review.repository

import org.whatismytree.wimt.review.repository.dto.ReviewSummary

interface ReviewQueryRepository {

    fun findAllByTreeId(treeId: Long): List<ReviewSummary>

    fun findById(reviewId: Long): ReviewSummary?
}
