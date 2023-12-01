package org.whatismytree.wimt.user.domain

import com.navercorp.fixturemonkey.kotlin.setNull
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.whatismytree.wimt.auth.domain.OAuthType
import org.whatismytree.wimt.support.createSample
import java.time.LocalDateTime

internal class UserTest {

    @Nested
    inner class SoftDelete {
        @Test
        fun `softDelete시 deleteAt에 현재 시간이 설정된다`() {
            // given
            val email = "test@email.com"
            val oauthType = OAuthType.KAKAO
            val oauthId = "sampleOAuthId"

            val user = User.of(email, oauthType, oauthId)
            val now = LocalDateTime.now()

            // when
            user.softDelete()

            // then
            assertThat(user.deletedAt).isAfterOrEqualTo(now)
        }
    }

    @Nested
    inner class UpdateProfile {
        @Test
        fun `updateProfile시 nickname과 profileImageUrl이 변경된다`() {
            // given
            val user: User = createSample {
                setNull(User::nickname)
                setNull(User::profileImageUrl)
            }

            val nickname = "nickname"
            val profileImageUrl = "profileImageUrl"

            // when
            user.updateProfile(nickname, profileImageUrl)

            // then
            assertThat(user.nickname).isEqualTo(nickname)
            assertThat(user.profileImageUrl).isEqualTo(profileImageUrl)
        }
    }
}
