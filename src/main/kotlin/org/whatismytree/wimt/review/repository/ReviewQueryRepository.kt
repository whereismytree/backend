package org.whatismytree.wimt.review.repository

import org.whatismytree.wimt.review.repository.dto.MyReviewResult
import org.whatismytree.wimt.review.repository.dto.ReviewDetailResult
import org.whatismytree.wimt.review.repository.dto.ReviewImageResult

interface ReviewQueryRepository {

    fun findAllByTreeId(treeId: Long): List<ReviewDetailResult>

    fun findAllImagesByTreeId(treeId: Long): List<ReviewImageResult>

    fun findById(reviewId: Long): ReviewDetailResult?

    fun findAllByUserId(userId: Long): List<MyReviewResult>
}
