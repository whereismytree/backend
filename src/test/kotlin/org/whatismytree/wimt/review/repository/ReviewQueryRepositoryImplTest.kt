package org.whatismytree.wimt.review.repository

import com.navercorp.fixturemonkey.kotlin.set
import com.navercorp.fixturemonkey.kotlin.setNull
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.TestConstructor
import org.whatismytree.wimt.config.QuerydslConfig
import org.whatismytree.wimt.review.domain.Review
import org.whatismytree.wimt.review.domain.ReviewTag
import org.whatismytree.wimt.review.domain.ReviewTags
import org.whatismytree.wimt.support.makeSample
import org.whatismytree.wimt.tag.domain.Tag
import org.whatismytree.wimt.user.domain.User

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Import(QuerydslConfig::class)
internal class ReviewQueryRepositoryImplTest(
    private val em: EntityManager,
) {

    private val reviewQueryRepository = ReviewQueryRepositoryImpl(JPAQueryFactory(em))

    @Nested
    inner class FindAll {

        @Test
        fun findAll() {
            // given
            val user = em.makeSample<User> {
                setNull(User::deletedAt)
            }
            val review = em.makeSample<Review> {
                set(Review::userId, user.id)
                set(Review::tags, ReviewTags())
                setNull(Review::deletedAt)
            }

            val tag1 = em.makeSample<Tag> {
                setNull(Tag::deletedAt)
            }

            val tag2 = em.makeSample<Tag> {
                setNull(Tag::deletedAt)
            }

            em.makeSample<ReviewTag> {
                set(ReviewTag::review, review)
                set(ReviewTag::tagId, tag1.id)
            }
            em.makeSample<ReviewTag> {
                set(ReviewTag::review, review)
                set(ReviewTag::tagId, tag2.id)
            }

            // when
            val result = reviewQueryRepository.findAllByTreeId(review.treeId)

            // then
            assertThat(result).hasSize(1)
            assertThat(result[0].id).isEqualTo(review.id)
            assertThat(result[0].authorNickname).isEqualTo(user.nickname)
            assertThat(result[0].authorProfileUrl).isEqualTo(user.profileImageUrl)
            assertThat(result[0].createdAt).isEqualTo(review.createdAt)
            assertThat(result[0].imageUrl).isEqualTo(review.imageUrl)
            assertThat(result[0].content).isEqualTo(review.content)
            assertThat(result[0].tags).containsExactlyInAnyOrder(tag1.content, tag2.content)
        }
    }
}
