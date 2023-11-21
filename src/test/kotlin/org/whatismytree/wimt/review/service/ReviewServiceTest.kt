package org.whatismytree.wimt.review.service

import com.navercorp.fixturemonkey.kotlin.set
import com.navercorp.fixturemonkey.kotlin.setNull
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.whatismytree.wimt.annotation.ServiceIntTest
import org.whatismytree.wimt.review.domain.Review
import org.whatismytree.wimt.review.domain.ReviewTag
import org.whatismytree.wimt.review.exception.ReviewInvalidPermissionException
import org.whatismytree.wimt.review.exception.ReviewNotFoundException
import org.whatismytree.wimt.review.exception.TagNotFoundException
import org.whatismytree.wimt.support.createLocalDateTime
import org.whatismytree.wimt.support.createSample
import org.whatismytree.wimt.support.makeSample
import org.whatismytree.wimt.tag.domain.Tag

@ServiceIntTest(ReviewService::class)
internal class ReviewServiceTest(
    private val reviewService: ReviewService,
    private val entityManager: EntityManager,
) {

    @Nested
    inner class CreateReview {

        @Test
        @DisplayName("리뷰를 생성한다")
        fun createReview() {
            // given
            val tag: Tag = entityManager.makeSample {
                setNull(Tag::createdAt)
                setNull(Tag::updatedAt)
                setNull(Tag::deletedAt)
            }

            // TODO: 트리 추가 필요

            val treeId = 1L
            val userId = 1L
            val content = "content"
            val tagIds = listOf(tag.id)
            val imageUrl = "imageUrl"

            // when
            val reviewId = reviewService.createReview(treeId, userId, content, tagIds, imageUrl)

            // then
            assertThat(reviewId).isNotNull()
        }

        // TODO: 트리가 존재하지 않으면 TreeNotFoundException을 발생시킨다

        @Test
        @DisplayName("태그가 존재하지 않으면 TagNotFoundException을 발생시킨다")
        fun notExistTag() {
            // given

            // TODO: 트리 추가 필요

            val treeId = 1L
            val userId = 1L
            val content = "content"
            val tagIds = listOf(1L, 2L)
            val imageUrl = "imageUrl"

            // when
            val result = catchThrowable {
                reviewService.createReview(treeId, userId, content, tagIds, imageUrl)
            }

            // then
            assertThat(result).isInstanceOf(TagNotFoundException::class.java)
        }
    }

    @Nested
    inner class DeleteReview {

        @Test
        @DisplayName("리뷰를 삭제한다")
        fun deleteReview() {
            // given
            val review = entityManager.makeSample {
                set(Review::userId, 10)
                set(Review::tags, listOf<ReviewTag>())
                setNull(Review::createdAt)
                setNull(Review::updatedAt)
                setNull(Review::deletedAt)
            }

            val reviewId = review.id
            val userId = review.userId
            val now = createLocalDateTime(2023)

            // when
            reviewService.deleteReview(reviewId, userId, now)

            // then
            val deletedReview = entityManager.find(Review::class.java, reviewId)
            assertThat(deletedReview).isNotNull()
            assertThat(deletedReview.deletedAt).isEqualTo(now)
        }

        @Test
        @DisplayName("리뷰가 존재하지 않으면 리뷰 NotFoundException가 발생한다")
        fun reviewNotFound() {
            // given
            val reviewId = 1L
            val userId = 1L

            // when
            val result = catchThrowable {
                reviewService.deleteReview(reviewId, userId)
            }

            // then
            assertThat(result).isInstanceOf(ReviewNotFoundException::class.java)
        }

        @Test
        @DisplayName("리뷰를 작성한 사용자가 아니면 ReviewInvalidPermissionException이 발생한다")
        fun invalidPermission() {
            // given
            val review = entityManager.makeSample {
                set(Review::userId, 10)
                set(Review::tags, listOf<ReviewTag>())
                setNull(Review::createdAt)
                setNull(Review::updatedAt)
                setNull(Review::deletedAt)
            }

            val reviewId = review.id
            val userId = 1L

            // when
            val result = catchThrowable {
                reviewService.deleteReview(reviewId, userId)
            }

            // then
            assertThat(result).isInstanceOf(ReviewInvalidPermissionException::class.java)
        }
    }
}
