package org.whatismytree.wimt.tree.repository

import com.navercorp.fixturemonkey.kotlin.set
import com.navercorp.fixturemonkey.kotlin.setNotNull
import com.navercorp.fixturemonkey.kotlin.setNull
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.whatismytree.wimt.annotation.RepositoryTest
import org.whatismytree.wimt.favorite.domain.Favorite
import org.whatismytree.wimt.review.domain.Review
import org.whatismytree.wimt.review.domain.ReviewTags
import org.whatismytree.wimt.support.makeSample
import org.whatismytree.wimt.tree.entity.Tree
import org.whatismytree.wimt.user.domain.User

@RepositoryTest
internal class TreeRepositoryImplTest(
    private val em: EntityManager,
) {

    private val treeRepositoryImpl = TreeRepositoryImpl(JPAQueryFactory(em))

    @Nested
    @DisplayName("유저가 등록한 트리 목록 조회")
    inner class FindPostedTreeList {
        @Test
        @DisplayName("유저가 등록한 트리들을 조회한다")
        fun findPostedTreeList() {
            // given
            val user = em.makeSample<User> {
                setNull(User::deletedAt)
                set(User::nickname, "short-nickname")
            }

            val tree = em.makeSample<Tree> {
                set(Tree::userId, user.id)
                setNull(Tree::deletedAt)
                set(Tree::addressType, "STREET")
                setNotNull(Tree::streetAddress)
            }
            repeat(2) {
                em.makeSample<Review> {
                    set(Review::treeId, tree.id)
                    set(Review::tags, ReviewTags())
                    setNull(Review::deletedAt)
                }
            }

            val tree2 = em.makeSample<Tree> {
                set(Tree::userId, user.id)
                setNull(Tree::deletedAt)
                set(Tree::addressType, "STREET")
                setNotNull(Tree::streetAddress)
            }
            repeat(3) {
                em.makeSample<Review> {
                    set(Review::treeId, tree2.id)
                    set(Review::tags, ReviewTags())
                    setNull(Review::deletedAt)
                }
            }

            // when
            val result = treeRepositoryImpl.findPostedTreeList(user.id)

            // then
            assertThat(result).hasSize(2)
                .extracting("reviewsCount")
                .containsExactlyInAnyOrder(2L, 3L)
        }

        @Test
        @DisplayName("삭제한 트리들은 조회되지 않는다")
        fun findPostedTreesWithoutDeletedTrees() {
            // given
            val user = em.makeSample<User> {
                setNull(User::deletedAt)
                set(User::nickname, "short-nickname")
            }

            repeat(3) {
                em.makeSample<Tree> {
                    set(Tree::userId, user.id)
                    setNull(Tree::deletedAt)
                    set(Tree::addressType, "STREET")
                    setNotNull(Tree::streetAddress)
                }
            }

            repeat(2) {
                em.makeSample<Tree> {
                    set(Tree::userId, user.id)
                    setNotNull(Tree::deletedAt)
                    set(Tree::addressType, "STREET")
                    setNotNull(Tree::streetAddress)
                }
            }

            // when
            val result = treeRepositoryImpl.findPostedTreeList(user.id)

            // then
            assertThat(result).hasSize(3)
                .extracting("reviewsCount")
                .containsOnly(0L)
        }
    }

    @Nested
    @DisplayName("유저가 즐겨찾기한 트리 목록 조회")
    inner class FindSavedTreeList {
        @Test
        @DisplayName("유저가 즐겨찾기한 트리들을 조회한다")
        fun findSavedTreeList() {
            // given
            val user = em.makeSample<User> {
                setNull(User::deletedAt)
                set(User::nickname, "short-nickname")
            }

            val tree = em.makeSample<Tree> {
                setNull(Tree::deletedAt)
                set(Tree::addressType, "STREET")
                setNotNull(Tree::streetAddress)
            }
            repeat(2) {
                em.makeSample<Review> {
                    set(Review::treeId, tree.id)
                    set(Review::tags, ReviewTags())
                    setNull(Review::deletedAt)
                }
            }

            val tree2 = em.makeSample<Tree> {
                setNull(Tree::deletedAt)
                set(Tree::addressType, "STREET")
                setNotNull(Tree::streetAddress)
            }
            repeat(3) {
                em.makeSample<Review> {
                    set(Review::treeId, tree2.id)
                    set(Review::tags, ReviewTags())
                    setNull(Review::deletedAt)
                }
            }

            em.makeSample<Favorite> {
                set(Favorite::userId, user.id)
                set(Favorite::treeId, tree.id)
            }

            em.makeSample<Favorite> {
                set(Favorite::userId, user.id)
                set(Favorite::treeId, tree2.id)
            }

            // when
            val result = treeRepositoryImpl.findSavedTreeList(user.id)

            // then
            assertThat(result).hasSize(2)
                .extracting("reviewsCount")
                .contains(2L, 3L)
        }
    }
}
