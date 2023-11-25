package org.whatismytree.wimt.review.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.whatismytree.wimt.review.domain.Review
import org.whatismytree.wimt.review.exception.ReviewInvalidPermissionException
import org.whatismytree.wimt.review.exception.ReviewNotFoundException
import org.whatismytree.wimt.review.exception.TagNotFoundException
import org.whatismytree.wimt.review.repository.ReviewRepository
import org.whatismytree.wimt.tag.repository.TagRepository
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class ReviewService(
    private val reviewRepository: ReviewRepository,
    private val tagRepository: TagRepository,
) {

    @Transactional
    fun createReview(treeId: Long, userId: Long, content: String, tagIds: List<Long>, imageUrl: String?): Long {
        // TODO: 트리 존재 여부 체크

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
