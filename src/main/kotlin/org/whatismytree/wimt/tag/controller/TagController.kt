package org.whatismytree.wimt.tag.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.whatismytree.wimt.tag.controller.dto.FindAllTagResponse
import org.whatismytree.wimt.tag.repository.TagQueryRepository

@Tag(name = "태그 API", description = "태그 API")
@RestController
@RequestMapping("/v1/tags")
class TagController(
    private val tagQueryRepository: TagQueryRepository,
) {

    @Operation(summary = "유효한 전체 태그를 조회한다")
    @GetMapping
    fun getTags(): FindAllTagResponse {
        val tags = tagQueryRepository.findAll()

        return FindAllTagResponse.of(tags)
    }
}
