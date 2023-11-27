package org.whatismytree.wimt.user.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.post
import org.whatismytree.wimt.annotation.WithMockOAuth2User
import org.whatismytree.wimt.common.ControllerTest
import org.whatismytree.wimt.user.service.UserService

@WebMvcTest(UserController::class)
internal class UserControllerTest : ControllerTest() {

    @MockkBean
    private lateinit var userService: UserService

    @Nested
    inner class CreateProfile {
        private val url = "/v1/my/profile"

        private fun createProfileRequest(
            nickname: String = "nickname",
            profileImageUrl: String = "https://profile.image.url",
        ): Map<String, Any> {
            return mapOf(
                "nickname" to nickname,
                "profileImageUrl" to profileImageUrl,
            )
        }

        @Test
        @DisplayName("공백만 있는 닉네임 입력에 대하여 400 Bad Request를 반환한다")
        fun nicknameIsBlank() {
            mockMvc.post(url) {
                jsonContent(createProfileRequest(nickname = "   "))
            }.andExpect {
                status { isBadRequest() }
            }
        }

        @Test
        @DisplayName("16자가 넘는 닉네임 입력에 대하여 400 Bad Request를 반환한다")
        fun nicknameLengthIsOver16() {
            mockMvc.post(url) {
                jsonContent(createProfileRequest(nickname = "a".repeat(17)))
            }.andExpect {
                status { isBadRequest() }
            }
        }

        @Test
        @DisplayName("프로필 사진 URL이 공백일 경우 400 Bad Request를 반환한다")
        fun profileImageUrlIsBlank() {
            mockMvc.post(url) {
                jsonContent(createProfileRequest(profileImageUrl = ""))
            }.andExpect {
                status { isBadRequest() }
            }
        }

        @Test
        @DisplayName("로그인이 되어있고 닉네임과 프로필 사진 URL이 올바르게 주어진 경우 200 OK를 반환한다")
        @WithMockOAuth2User
        fun createProfile() {
            // given
            every { userService.createProfile(any(), any(), any()) } just Runs

            // when then
            mockMvc.post(url) {
                jsonContent(createProfileRequest())
            }.andExpect {
                status { isOk() }
            }
        }
    }
}
