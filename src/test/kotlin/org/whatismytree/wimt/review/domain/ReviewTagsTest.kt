package org.whatismytree.wimt.review.domain

import com.navercorp.fixturemonkey.kotlin.set
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.whatismytree.wimt.support.createSample

internal class ReviewTagsTest {

    @Nested
    inner class UpdateTags {

        @Test
        @DisplayName("기존 태그 목록에 없는 태그만 추가한다")
        fun updateTags() {
            // given
            val reviewTags = createSample<ReviewTags> {
                set(
                    "value",
                    listOf(
                        createSample<ReviewTag> {
                            set(ReviewTag::tagId, 1L)
                        },
                        createSample<ReviewTag> {
                            set(ReviewTag::tagId, 2L)
                        },
                    ),
                )
            }

            // when
            reviewTags.update(
                review = createSample<Review> {
                    set(Review::tags, reviewTags)
                },
                tagIds = listOf(1L, 2L, 3L),
            )

            // then
            assertThat(reviewTags.getValue()).hasSize(3)
            assertThat(reviewTags.getValue().map { it.tagId }).containsAll(listOf(1L, 2L, 3L))
        }

        @Test
        @DisplayName("기존 태그 목록 중 변경된 태그 목록에 포함되지 않는 태그는 삭제한다")
        fun updateTagsWithDeleteTag() {
            // given
            val reviewTags = createSample<ReviewTags> {
                set(
                    "value",
                    listOf(
                        createSample<ReviewTag> {
                            set(ReviewTag::tagId, 1L)
                        },
                        createSample<ReviewTag> {
                            set(ReviewTag::tagId, 2L)
                        },
                    ),
                )
            }

            // when
            reviewTags.update(
                review = createSample<Review> {
                    set(Review::tags, reviewTags)
                },
                tagIds = listOf(1L),
            )

            // then
            assertThat(reviewTags.getValue()).hasSize(1)
            assertThat(reviewTags.getValue().map { it.tagId }).containsAll(listOf(1L))
        }
    }
}
