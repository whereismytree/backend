package org.whatismytree.wimt.review.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class ReviewTest {

    @Nested
    inner class Of {

        @Test
        @DisplayName("리뷰를 생성한다")
        fun createReview() {
            // given
            val treeId = 1L
            val userId = 1L
            val contents = "content"
            val tagIds = listOf(1L, 2L)
            val imageUrl = "imageUrl"

            // when
            val review = Review.of(treeId, userId, contents, tagIds, imageUrl)

            // then
            assertThat(review.treeId).isEqualTo(treeId)
            assertThat(review.userId).isEqualTo(userId)
            assertThat(review.contents).isEqualTo(contents)
            assertThat(review.imageUrl).isEqualTo(imageUrl)
            assertThat(review.tags).hasSize(2)

            val tagIdsInReview = review.tags.map { it.tagId }
            assertThat(tagIdsInReview).containsAll(tagIds)
        }
    }

    @Nested
    inner class AddTags {

        @Test
        @DisplayName("태그를 추가한다")
        fun addTags() {
            // given
            val review = Review.of(
                treeId = 1L,
                userId = 1L,
                content = "content",
                tagIds = listOf()
            )

            // when
            review.addTags(listOf(1L, 2L, 3L))

            // then
            assertThat(review.tags).hasSize(3)
        }

        @Test
        @DisplayName("이미 추가된 태그는 추가하지 않는다")
        fun addTagsWithAlreadyAddedTag() {
            // given
            val review = Review.of(
                treeId = 1L,
                userId = 1L,
                content = "content",
                tagIds = listOf(1L)
            )

            // when
            review.addTags(listOf(1L, 2L, 3L))

            // then
            assertThat(review.tags).hasSize(3)
        }
    }
}