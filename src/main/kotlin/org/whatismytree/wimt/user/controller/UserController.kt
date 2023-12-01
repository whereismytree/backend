package org.whatismytree.wimt.user.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.whatismytree.wimt.common.CurrentUserId
import org.whatismytree.wimt.user.controller.dto.CreateProfileRequest
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
}
