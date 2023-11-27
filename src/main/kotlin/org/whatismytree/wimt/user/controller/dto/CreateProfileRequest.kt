package org.whatismytree.wimt.user.controller.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateProfileRequest(
    @field:NotBlank
    @field:Size(min = 1, max = 16)
    val nickname: String,
    @field:NotBlank
    val profileImageUrl: String,
)
