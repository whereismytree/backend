package org.whatismytree.wimt.review.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.whatismytree.wimt.review.controller.dto.CreateReviewRequest
import org.whatismytree.wimt.review.controller.dto.CreateReviewResponse
import org.whatismytree.wimt.review.controller.dto.GetReviewImagesResponse
import org.whatismytree.wimt.review.controller.dto.GetReviewsResponse
import org.whatismytree.wimt.review.controller.dto.UpdateReviewRequest
import org.whatismytree.wimt.review.service.ReviewService

@Tag(name = "후기 API", description = "후기 API")
@RestController
@RequestMapping("/v1/reviews")
class ReviewController(
    private val reviewService: ReviewService,
) {

    @Operation(summary = "후기를 생성한다")
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

    @Operation(summary = "후기 목록을 조회한다")
    @GetMapping
    fun getReviews(
        @RequestParam(required = true) treeId: Long,
    ): GetReviewsResponse {
        val reviews = reviewService.findAllDetail(treeId)

        return GetReviewsResponse.of(reviews)
    }

    @Operation(summary = "후기 이미지 목록을 조회한다")
    @GetMapping("/images")
    fun getReviewImages(
        @RequestParam(required = true) treeId: Long,
    ): GetReviewImagesResponse {
        val reviews = reviewService.findAllImage(treeId)

        return GetReviewImagesResponse.of(reviews)
    }

    @Operation(summary = "후기를 조회한다")
    @GetMapping("/{reviewId}")
    fun getReview(
        @Min(1) @PathVariable reviewId: Long,
        // TODO: 시큐리티 작업 이후 AuthenticationPrincipal 통해 가져오는 값으로 변경
    ) = reviewService.getDetailById(reviewId, 1L)

    @Operation(summary = "후기를 수정한다")
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

    @Operation(summary = "후기를 삭제한다")
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
