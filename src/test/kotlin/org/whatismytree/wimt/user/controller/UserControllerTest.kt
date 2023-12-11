package org.whatismytree.wimt.user.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.whatismytree.wimt.annotation.WithMockOAuth2User
import org.whatismytree.wimt.auth.domain.OAuthType
import org.whatismytree.wimt.common.ControllerTest
import org.whatismytree.wimt.user.controller.dto.CheckAvailableResponse
import org.whatismytree.wimt.user.controller.dto.GetMyPageResponse
import org.whatismytree.wimt.user.exception.DuplicatedNicknameException
import org.whatismytree.wimt.user.exception.UserNotFoundException
import org.whatismytree.wimt.user.repository.dto.UserDetailResult
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
        @DisplayName("인증하지 않은 사용자일 경우 401 Unauthorized를 반환한다")
        fun unauthorized() {
            // when then
            mockMvc.post(url) {
                jsonContent(createProfileRequest())
            }.andExpect {
                status { isUnauthorized() }
            }
        }

        @Test
        @DisplayName("존재하지 않는 유저 ID일 경우 400 Bad Request를 반환한다")
        @WithMockOAuth2User(userId = 1L)
        fun userNotFoundException() {
            // given
            every {
                userService.createProfile(
                    any(),
                    any(),
                    any(),
                )
            } throws UserNotFoundException("존재하지 않는 유저입니다. userId: 1")

            // when then
            mockMvc.post(url) {
                jsonContent(createProfileRequest())
            }.andExpect {
                status { isBadRequest() }
            }
        }

        @Test
        @DisplayName("중복되는 닉네임일 경우 400 Bad Request를 반환한다")
        @WithMockOAuth2User
        fun duplicatedNicknameException() {
            // given
            every {
                userService.createProfile(
                    any(),
                    any(),
                    any(),
                )
            } throws DuplicatedNicknameException("이미 존재하는 닉네임입니다. nickname: duplicatedNickname")

            // when then
            mockMvc.post(url) {
                jsonContent(createProfileRequest())
            }.andExpect {
                status { isBadRequest() }
            }
        }

        @Test
        @DisplayName("로그인이 되어있고 중복되지 않는 닉네임과 프로필 사진 URL이 올바르게 주어진 경우 200 OK를 반환한다")
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

    @Nested
    inner class GetMyPage {
        private val url = "/v1/my"

        @Test
        @DisplayName("로그인이 되어있지 않은 경우 401 Unauthorized를 반환한다")
        fun unauthorized() {
            // when then
            mockMvc.get(url).andExpect {
                status { isUnauthorized() }
            }
        }

        @Test
        @DisplayName("로그인이 되어있고 유저의 닉네임이 존재하지 않는 경우 400 Bad Request를 반환한다")
        @WithMockOAuth2User(userId = 1L)
        fun nicknameIsNull() {
            // given
            every { userService.getDetailById(any()) } throws IllegalStateException("닉네임이 존재하지 않습니다. userId: 1")

            // when then
            mockMvc.get(url).andExpect {
                status { isBadRequest() }
            }
        }

        @Test
        @DisplayName("로그인이 되어있고 유저의 프로필 사진 URL이 존재하지 않는 경우 400 Bad Request를 반환한다")
        @WithMockOAuth2User(userId = 1L)
        fun profileImageUrlIsNull() {
            // given
            every { userService.getDetailById(any()) } throws IllegalStateException("프로필 이미지가 존재하지 않습니다. userId: 1")

            // when then
            mockMvc.get(url).andExpect {
                status { isBadRequest() }
            }
        }

        @Test
        @DisplayName("로그인이 되어있고 유저의 닉네임과 프로필 사진 URL이 존재하는 경우 200 OK를 반환한다")
        @WithMockOAuth2User(userId = 1L)
        fun getMyPage() {
            // given
            val serviceResponse = UserDetailResult(
                nickname = "nickname",
                email = "email",
                oauthType = OAuthType.GOOGLE,
                profileImageUrl = "https://profile.image.url",
                postedTreesCount = 1L,
                savedTreesCount = 1L,
                reviewsCount = 1L,
            )

            val response = GetMyPageResponse.of(serviceResponse)

            every { userService.getDetailById(any()) } returns serviceResponse

            // when then
            mockMvc.get(url)
                .andExpect {
                    status { isOk() }
                    content { success(response) }
                }
        }
    }

    @Nested
    inner class CheckNicknameAvailable {
        private val url = "/v1/my/check/nickname"

        @Test
        @DisplayName("닉네임을 입력 안했을 경우 400 Bad Request를 반환한다")
        fun nicknameNotEntered() {
            // when then
            mockMvc.get(url)
                .andExpect {
                    status { isBadRequest() }
                }
        }

        @Test
        @DisplayName("닉네임이 중복될 경우 200 OK와 false를 반환한다")
        fun nicknameIsDuplicated() {
            // given
            every { userService.checkNicknameAvailable(any()) } returns false
            val response = CheckAvailableResponse(false)

            // when then
            mockMvc.get("$url?nickname=duplicatedNickname")
                .andExpect {
                    status { isOk() }
                    content { success(response) }
                }
        }

        @Test
        @DisplayName("닉네임이 중복되지 않을 경우 200 OK와 true를 반환한다")
        fun nicknameIsNotDuplicated() {
            // given
            every { userService.checkNicknameAvailable(any()) } returns true
            val response = CheckAvailableResponse(true)

            // when then
            mockMvc.get("$url?nickname=notDuplicatedNickname")
                .andExpect {
                    status { isOk() }
                    content { success(response) }
                }
        }
    }

    @Nested
    inner class DeleteUser {
        private val url = "/v1/my"

        @Test
        @DisplayName("로그인이 되어있지 않은 경우 401 Unauthorized를 반환한다")
        fun unauthorized() {
            // when then
            mockMvc.delete(url).andExpect {
                status { isUnauthorized() }
            }
        }

        @Test
        @DisplayName("존재하지 않는 유저일 경우 400 Bad Request를 반환한다")
        @WithMockOAuth2User(userId = 1L)
        fun userNotFound() {
            // given
            every { userService.deleteUser(any()) } throws UserNotFoundException("존재하지 않는 유저입니다. userId: 1")

            // when then
            mockMvc.delete(url).andExpect {
                status { isBadRequest() }
            }
        }

        @Test
        @DisplayName("유저를 삭제한다")
        @WithMockOAuth2User(userId = 1L)
        fun deleteUser() {
            // given
            every { userService.deleteUser(any()) } just Runs

            // when then
            mockMvc.delete(url).andExpect {
                status { isOk() }
            }
        }
    }
}
