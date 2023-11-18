package org.whatismytree.wimt.review.service

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.whatismytree.wimt.annotation.ServiceIntTest
import org.whatismytree.wimt.review.exception.TagNotFoundException
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
            val tag = Tag.of("tag1")
            entityManager.persist(tag)

            // TODO: 트리 추가 필요

            val treeId = 1L
            val userId = 1L
            val content = "content"
            val tagIds = listOf(tag.id!!)
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
}