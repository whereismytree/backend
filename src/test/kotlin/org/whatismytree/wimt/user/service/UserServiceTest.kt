package org.whatismytree.wimt.user.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.whatismytree.wimt.annotation.ServiceIntTest
import org.whatismytree.wimt.auth.domain.OAuthType
import org.whatismytree.wimt.auth.dto.OAuthInfo
import org.whatismytree.wimt.user.domain.User

private const val s = "test@google.com"

@ServiceIntTest(UserService::class)
internal class UserServiceTest(
    private val userService: UserService,
    private val entityManager: TestEntityManager
) {

    @Nested
    inner class FindOrCreateUser {

        @Test
        @DisplayName("OAuthInfo로 회원을 생성한다")
        fun createUser() {
            // given
            val oAuthInfo = OAuthInfo(
                OAuthType.GOOGLE,
                "ThisIsSampleOAuthId",
                "test@google.com"
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

            val existUser = User(
                email = email,
                oauthType = oAuthType,
                oauthId = oAuthId)
            val existUserId = entityManager.persistAndGetId(existUser)

            val oAuthInfo = OAuthInfo(
                email = email,
                oAuthType = oAuthType,
                oAuthId = oAuthId
            )

            // when
            val findUser = userService.findOrCreateUser(oAuthInfo)

            // then
            assertThat(findUser.id).isEqualTo(existUserId)
        }
    }
}