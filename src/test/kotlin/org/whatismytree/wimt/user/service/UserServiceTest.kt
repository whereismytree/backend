package org.whatismytree.wimt.user.service

import com.navercorp.fixturemonkey.kotlin.set
import com.navercorp.fixturemonkey.kotlin.setNull
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.whatismytree.wimt.annotation.ServiceIntTest
import org.whatismytree.wimt.auth.domain.OAuthType
import org.whatismytree.wimt.auth.dto.OAuthInfo
import org.whatismytree.wimt.favorite.domain.Favorite
import org.whatismytree.wimt.review.domain.Review
import org.whatismytree.wimt.review.domain.ReviewTags
import org.whatismytree.wimt.support.makeSample
import org.whatismytree.wimt.tree.entity.Tree
import org.whatismytree.wimt.user.domain.User
import org.whatismytree.wimt.user.exception.DuplicatedNicknameException
import org.whatismytree.wimt.user.exception.UserNotFoundException

@ServiceIntTest(UserService::class)
internal class UserServiceTest(
    private val userService: UserService,
    private val entityManager: TestEntityManager,
) {

    @Nested
    inner class FindOrCreateUser {

        @Test
        @DisplayName("OauthInfo와 일치하는 회원이 없다면 회원을 생성한다.")
        fun createUser() {
            // given
            val oAuthInfo = OAuthInfo(
                OAuthType.GOOGLE,
                "ThisIsSampleOAuthId",
                "test@google.com",
            )

            // when
            val user = userService.findOrCreateUser(oAuthInfo)

            // then
            assertThat(user.id).isNotNull()
        }

        @Test
        @DisplayName("이미 존재하는 회원이면 회원을 조회한다")
        fun findUser() {
            // given
            val oAuthType = OAuthType.GOOGLE
            val oAuthId = "ThisIsSampleOAuthId"
            val email = "test@google.com"

            val existUser = User.of(
                email = email,
                oauthType = oAuthType,
                oauthId = oAuthId,
            )
            val existUserId = entityManager.persistAndGetId(existUser)

            val oAuthInfo = OAuthInfo(
                email = email,
                oAuthType = oAuthType,
                oAuthId = oAuthId,
            )

            // when
            val findUser = userService.findOrCreateUser(oAuthInfo)

            // then
            assertThat(findUser.id).isEqualTo(existUserId)
        }
    }

    @Nested
    inner class CreateProfile {

        @Test
        @DisplayName("존재하지 않는 유저이면 UserNotFoundException이 발생한다.")
        fun userNotFound() {
            // given
            val userId = 1L
            val nickname = "nickname"
            val profileImageUrl = "profileImageUrl"

            // when
            val result = catchThrowable {
                userService.createProfile(
                    userId = userId,
                    nickname = nickname,
                    profileImageUrl = profileImageUrl,
                )
            }

            // then
            assertThat(result).isInstanceOf(UserNotFoundException::class.java)
        }

        @Test
        @DisplayName("닉네임이 중복되면 DuplicatedNicknameException이 발생한다.")
        fun duplicatedNickname() {
            // given
            val existNickname = "existNickname"
            val profileImageUrl = "profileImageUrl"

            entityManager.makeSample<User> {
                set(User::nickname, existNickname)
            }

            val user = entityManager.makeSample<User> {
                setNull(User::nickname)
                setNull(User::profileImageUrl)
            }

            // when
            val result = catchThrowable {
                userService.createProfile(
                    userId = user.id,
                    nickname = existNickname,
                    profileImageUrl = profileImageUrl,
                )
            }

            // then
            assertThat(result).isInstanceOf(DuplicatedNicknameException::class.java)
        }

        @Test
        @DisplayName("닉네임이 중복되지 않으면 프로필을 생성한다.")
        fun createProfile() {
            // given
            val nickname = "nickname"
            val profileImageUrl = "profileImageUrl"

            val user = entityManager.makeSample<User> {
                setNull(User::nickname)
                setNull(User::profileImageUrl)
            }

            // when
            userService.createProfile(
                userId = user.id,
                nickname = nickname,
                profileImageUrl = profileImageUrl,
            )

            // then
            val findUser = entityManager.find(User::class.java, user.id)
            assertThat(findUser.nickname).isEqualTo(nickname)
            assertThat(findUser.profileImageUrl).isEqualTo(profileImageUrl)
        }
    }

    @Nested
    inner class GetDetailById {

        @Test
        @DisplayName("존재하지 않는 유저이면 UserNotFoundException이 발생한다.")
        fun userNotFound() {
            // given
            val nonExistsUserId = 1L

            // when
            val result = catchThrowable {
                userService.getDetailById(nonExistsUserId)
            }

            // then
            assertThat(result).isInstanceOf(UserNotFoundException::class.java)
        }

        @Test
        @DisplayName("닉네임이 존재하지 않으면 IllegalStateException이 발생한다.")
        fun nicknameNotFound() {
            // given
            val user = entityManager.makeSample<User> {
                setNull(User::nickname)
                set(User::profileImageUrl, "profileImageUrl")
                setNull(User::deletedAt)
            }

            // when
            val result = catchThrowable {
                userService.getDetailById(user.id)
            }

            // then
            assertThat(result).isInstanceOf(IllegalStateException::class.java)
        }

        @Test
        @DisplayName("프로필 이미지가 존재하지 않으면 IllegalStateException이 발생한다.")
        fun profileImageUrlNotFound() {
            // given
            val user = entityManager.makeSample<User> {
                set(User::nickname, "nickname")
                setNull(User::profileImageUrl)
                setNull(User::deletedAt)
            }

            // when
            val result = catchThrowable {
                userService.getDetailById(user.id)
            }

            // then
            assertThat(result).isInstanceOf(IllegalStateException::class.java)
        }

        @Test
        @DisplayName("유저 정보를 반환한다.")
        fun getDetailById() {
            // given
            val user = entityManager.makeSample<User> {
                set(User::nickname, "nickname")
                set(User::profileImageUrl, "profileImageUrl")
                setNull(User::deletedAt)
            }
            val tree = entityManager.makeSample<Tree> {
                set(Tree::userId, user.id)
                setNull(Tree::deletedAt)
            }
            entityManager.makeSample<Review> {
                set(Review::userId, user.id)
                set(Review::treeId, tree.id)
                set(Review::tags, ReviewTags())
                setNull(Review::deletedAt)
            }
            entityManager.makeSample<Favorite> {
                set(Favorite::userId, user.id)
                set(Favorite::treeId, tree.id)
            }

            // when
            val result = userService.getDetailById(user.id)

            // then
            assertThat(result.nickname).isEqualTo(user.nickname)
            assertThat(result.email).isEqualTo(user.email)
            assertThat(result.oauthType).isEqualTo(user.oauthType)
            assertThat(result.profileImageUrl).isEqualTo(user.profileImageUrl)
            assertThat(result.postedTreesCount).isEqualTo(1)
            assertThat(result.savedTreesCount).isEqualTo(1)
            assertThat(result.reviewsCount).isEqualTo(1)
        }
    }

    @Nested
    inner class CheckNicknameAvailable {
        @Test
        @DisplayName("닉네임이 중복되면 false를 반환한다.")
        fun duplicatedNickname() {
            // given
            val existNickname = "existNickname"

            entityManager.makeSample<User> {
                set(User::nickname, existNickname)
            }

            // when
            val result = userService.checkNicknameAvailable(existNickname)

            // then
            assertThat(result).isFalse()
        }

        @Test
        @DisplayName("닉네임이 중복되지 않으면 true를 반환한다.")
        fun availableNickname() {
            // given
            val nickname = "nickname"

            // when
            val result = userService.checkNicknameAvailable(nickname)

            // then
            assertThat(result).isTrue()
        }
    }

    @Nested
    inner class DeleteUser {
        @Test
        @DisplayName("존재하지 않는 유저이면 UserNotFoundException이 발생한다.")
        fun userNotFound() {
            // given
            val nonExistsUserId = 1L

            // when
            val result = catchThrowable {
                userService.deleteUser(nonExistsUserId)
            }

            // then
            assertThat(result).isInstanceOf(UserNotFoundException::class.java)
        }

        @Test
        @DisplayName("유저를 삭제한다.")
        fun deleteUser() {
            // given
            val user = entityManager.makeSample<User> {
                setNull(User::deletedAt)
            }

            // when
            userService.deleteUser(user.id)

            // then
            assertThat(user.deletedAt).isNotNull()
        }
    }
}
