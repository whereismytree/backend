package org.whatismytree.wimt.review.domain

import com.navercorp.fixturemonkey.kotlin.set
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
            val content = "content"
            val tagIds = listOf(1L, 2L)
            val imageUrl = "imageUrl"

            // when
            val review = Review.of(treeId, userId, content, tagIds, imageUrl)

            // then
            assertThat(review.treeId).isEqualTo(treeId)
            assertThat(review.userId).isEqualTo(userId)
            assertThat(review.content).isEqualTo(content)
            assertThat(review.imageUrl).isEqualTo(imageUrl)
            assertThat(review.tags.getValue()).hasSize(2)

            val tagIdsInReview = review.tags.getValue().map { it.tagId }
            assertThat(tagIdsInReview).containsAll(tagIds)
        }
    }
}
