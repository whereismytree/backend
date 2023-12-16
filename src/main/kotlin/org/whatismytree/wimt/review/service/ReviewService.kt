package org.whatismytree.wimt.review.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.whatismytree.wimt.review.controller.dto.GetReviewResponse
import org.whatismytree.wimt.review.domain.Review
import org.whatismytree.wimt.review.exception.ReviewInvalidPermissionException
import org.whatismytree.wimt.review.exception.ReviewNotFoundException
import org.whatismytree.wimt.review.exception.TagNotFoundException
import org.whatismytree.wimt.review.repository.ReviewQueryRepository
import org.whatismytree.wimt.review.repository.ReviewRepository
import org.whatismytree.wimt.review.repository.dto.ReviewDetailResult
import org.whatismytree.wimt.review.repository.dto.ReviewImageResult
import org.whatismytree.wimt.tag.repository.TagRepository
import org.whatismytree.wimt.tree.exception.TreeNotFoundException
import org.whatismytree.wimt.tree.repository.TreeRepository
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class ReviewService(
    private val reviewRepository: ReviewRepository,
    private val reviewQueryRepository: ReviewQueryRepository,
    private val tagRepository: TagRepository,
    private val treeRepository: TreeRepository,
) {

    fun findAllDetail(treeId: Long): List<ReviewDetailResult> {
        return reviewQueryRepository.findAllByTreeId(treeId)
    }

    fun findAllImage(treeId: Long): List<ReviewImageResult> {
        return reviewQueryRepository.findAllImagesByTreeId(treeId)
    }

    fun getDetailById(reviewId: Long, userId: Long): GetReviewResponse {
        val reviewSummary = reviewQueryRepository.findById(reviewId)
            ?: throw ReviewNotFoundException("존재하지 않는 리뷰입니다. reviewId: $reviewId")

        val canEdit = reviewSummary.authorId == userId
        val canRemove = reviewSummary.authorId == userId

        return GetReviewResponse.of(reviewSummary, canEdit, canRemove)
    }

    @Transactional
    fun createReview(treeId: Long, userId: Long, content: String, tagIds: List<Long>, imageUrl: String): Long {
        treeRepository.findByIdAndDeletedAtIsNull(treeId)
            ?: throw TreeNotFoundException("해당하는 트리가 존재하지 않습니다 treeId=$treeId")

        val tags = tagRepository.findAllByDeletedAtIsNullAndIdIn(tagIds)
        if (tags.size != tagIds.size) {
            throw TagNotFoundException("태그가 존재하지 않습니다. tagIds: $tagIds")
        }

        val review = Review.of(treeId, userId, content, tagIds, imageUrl)

        reviewRepository.save(review)

        return review.id
    }

    @Transactional
    fun updateReview(userId: Long, reviewId: Long, content: String?, tagIds: List<Long>?, imageUrl: String?) {
        val review = reviewRepository.findById(reviewId)
            .orElseThrow { throw ReviewNotFoundException("존재하지 않는 리뷰입니다. reviewId: $reviewId") }

        if (!review.isAuthor(userId)) {
            throw ReviewInvalidPermissionException("수정 권한이 없습니다. reviewId: $reviewId, userId: $userId")
        }

        review.update(content, tagIds, imageUrl)
    }

    @Transactional
    fun deleteReview(reviewId: Long, userId: Long, now: LocalDateTime = LocalDateTime.now()) {
        val review = reviewRepository.findById(reviewId)
            .orElseThrow { throw ReviewNotFoundException("존재하지 않는 리뷰입니다. reviewId: $reviewId") }

        if (!review.isAuthor(userId)) {
            throw ReviewInvalidPermissionException("삭제 권한이 없습니다. reviewId: $reviewId, userId: $userId")
        }

        review.delete(now)
    }
}
