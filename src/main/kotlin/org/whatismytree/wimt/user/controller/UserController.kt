package org.whatismytree.wimt.user.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.whatismytree.wimt.common.CurrentUserId
import org.whatismytree.wimt.user.controller.dto.CheckAvailableResponse
import org.whatismytree.wimt.user.controller.dto.CreateProfileRequest
import org.whatismytree.wimt.user.controller.dto.GetMyPageResponse
import org.whatismytree.wimt.user.service.UserService

@Tag(name = "유저 API", description = "유저 API")
@RestController
@RequestMapping("/v1/my")
class UserController(
    private val userService: UserService,
) {

    @Operation(summary = "프로필을 생성한다")
    @PostMapping("/profile")
    fun createProfile(
        @Valid @RequestBody
        request: CreateProfileRequest,
        @CurrentUserId userId: Long,
    ) {
        userService.createProfile(
            userId = userId,
            nickname = request.nickname,
            profileImageUrl = request.profileImageUrl,
        )
    }

    @Operation(summary = "마이페이지 조회")
    @GetMapping
    fun getMyPage(
        @CurrentUserId userId: Long,
    ): GetMyPageResponse {
        val userDetail = userService.getDetailById(userId)

        return GetMyPageResponse.of(userDetail)
    }

    @Operation(summary = "닉네임 사용 가능 여부 조회")
    @GetMapping("/check")
    fun checkAvailable(
        @RequestParam nickname: String,
    ): CheckAvailableResponse {
        val available = userService.checkNicknameAvailable(nickname)

        return CheckAvailableResponse(available)
    }
}
