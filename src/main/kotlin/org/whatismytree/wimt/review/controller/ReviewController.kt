package org.whatismytree.wimt.review.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.whatismytree.wimt.review.controller.dto.CreateReviewRequest
import org.whatismytree.wimt.review.controller.dto.CreateReviewResponse
import org.whatismytree.wimt.review.controller.dto.UpdateReviewRequest
import org.whatismytree.wimt.review.service.ReviewService

@Tag(name = "리뷰 API", description = "리뷰 API")
@RestController
@RequestMapping("/v1/reviews")
class ReviewController(
    private val reviewService: ReviewService,
) {

    @Operation(summary = "리뷰를 생성한다")
    @PostMapping
    fun createReview(
        @Valid @RequestBody
        request: CreateReviewRequest,
    ): CreateReviewResponse {
        val reviewId = reviewService.createReview(
            treeId = request.treeId,
            // TODO: 시큐리티 작업 이후 AuthenticationPrincipal 통해 가져오는 값으로 변경
            userId = 1L,
            content = request.content,
            tagIds = request.tagIds,
            imageUrl = request.imageUrl,
        )

        return CreateReviewResponse(reviewId)
    }

    @Operation(summary = "리뷰를 수정한다")
    @PutMapping("/{reviewId}")
    fun updateReview(
        @Min(1) @PathVariable reviewId: Long,
        @Valid @RequestBody
        request: UpdateReviewRequest,
    ) {
        reviewService.updateReview(
            // TODO: 시큐리티 작업 이후 AuthenticationPrincipal 통해 가져오는 값으로 변경
            userId = 1L,
            reviewId = reviewId,
            content = request.content,
            tagIds = request.tagIds,
            imageUrl = request.imageUrl,
        )
    }

    @Operation(summary = "리뷰를 삭제한다")
    @DeleteMapping("/{reviewId}")
    fun deleteReview(
        @Min(1) @PathVariable reviewId: Long,
    ) {
        reviewService.deleteReview(
            reviewId = reviewId,
            // TODO: 시큐리티 작업 이후 AuthenticationPrincipal 통해 가져오는 값으로 변경
            userId = 1L,
        )
    }
}
