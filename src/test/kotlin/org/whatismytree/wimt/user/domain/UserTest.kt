package org.whatismytree.wimt.user.domain

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

import org.whatismytree.wimt.auth.domain.OAuthType
import java.time.LocalDateTime

class UserTest {

    @Test
    fun `softDelete시 deleteAt에 현재 시간이 설정된다`() {
        // given
        val user = User("test@email.com", OAuthType.KAKAO, "sampleOAuthId")
        val now = LocalDateTime.now()

        // when
        user.softDelete()

        // then
        assertThat(user.deletedAt).isAfterOrEqualTo(now)
    }
}