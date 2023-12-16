package org.whatismytree.wimt.user.repository

import com.navercorp.fixturemonkey.kotlin.set
import com.navercorp.fixturemonkey.kotlin.setNull
import io.kotest.core.spec.style.ExpectSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.whatismytree.wimt.annotation.RepositoryTest
import org.whatismytree.wimt.favorite.domain.Favorite
import org.whatismytree.wimt.review.domain.Review
import org.whatismytree.wimt.review.domain.ReviewTags
import org.whatismytree.wimt.support.makeSample
import org.whatismytree.wimt.tree.entity.Tree
import org.whatismytree.wimt.user.domain.User

@RepositoryTest
internal class UserRepositoryTest(
    private val em: TestEntityManager,
    private val userRepository: UserRepository,
) : ExpectSpec(
    {
        extensions(SpringExtension)

        context("findUserDetailById") {
            lateinit var user: User

            beforeTest {
                user = em.makeSample<User> {
                    set(User::nickname, "nickname")
                    setNull(User::deletedAt)
                }
                val tree = em.makeSample<Tree> {
                    set(Tree::userId, user.id)
                    setNull(Tree::deletedAt)
                }
                em.makeSample<Review> {
                    set(Review::userId, user.id)
                    set(Review::treeId, tree.id)
                    set(Review::tags, ReviewTags())
                    setNull(Review::deletedAt)
                }
                em.makeSample<Favorite> {
                    set(Favorite::userId, user.id)
                    set(Favorite::treeId, tree.id)
                }
            }

            expect("존재하지 않는 유저의 경우 null을 반환한다.") {
                val nonExistsUserId = user.id + 1
                val result = userRepository.findUserDetailById(nonExistsUserId)

                result shouldBe null
            }

            expect("존재하는 유저의 경우 유저 정보를 반환한다.") {
                val result = userRepository.findUserDetailById(user.id)

                result?.nickname shouldBe user.nickname
                result?.email shouldBe user.email
                result?.oauthType shouldBe user.oauthType
                result?.profileImageUrl shouldBe user.profileImageUrl
                result?.postedTreesCount shouldBe 1
                result?.savedTreesCount shouldBe 1
                result?.reviewsCount shouldBe 1
            }
        }
    },
)
