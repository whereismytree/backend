package org.whatismytree.wimt.review.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.whatismytree.wimt.common.CurrentUserId
import org.whatismytree.wimt.review.controller.dto.MyReviewResponse
import org.whatismytree.wimt.review.repository.ReviewQueryRepository

@RestController
@RequestMapping("/v1/my/review")
@Tag(name = "내 후기 API", description = "내 후기 API")
class MyReviewController(
    private val reviewQueryRepository: ReviewQueryRepository,
) {

    @GetMapping
    @Operation(summary = "내 후기 목록을 조회한다")
    fun getMyReviews(
        @CurrentUserId userId: Long,
    ): MyReviewResponse {
        val result = reviewQueryRepository.findAllByUserId(userId)

        return MyReviewResponse.of(result)
    }
}
