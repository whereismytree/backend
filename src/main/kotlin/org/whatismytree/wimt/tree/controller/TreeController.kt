package org.whatismytree.wimt.tree.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.whatismytree.wimt.common.CurrentUserId
import org.whatismytree.wimt.tree.controller.dto.CreateTreeRequest
import org.whatismytree.wimt.tree.controller.dto.FindTreeListResponse
import org.whatismytree.wimt.tree.controller.dto.FindTreeMapResponse
import org.whatismytree.wimt.tree.controller.dto.FindTreeResponse
import org.whatismytree.wimt.tree.controller.dto.UpdateTreeRequest
import org.whatismytree.wimt.tree.service.TreeService

@Validated
@RestController
@Tag(name = "트리 관리")
@RequestMapping("/v1/trees")
class TreeController(
    private val treeService: TreeService,
) {
    @PostMapping("")
    @Operation(summary = "트리 등록")
    @Throws(Exception::class)
    fun createTree(
        @Valid @RequestBody
        req: CreateTreeRequest,
        @CurrentUserId userId: Long,
    ) =
        treeService.createTree(req, userId)

    @GetMapping("{id}")
    @Operation(summary = "트리 조회")
    @Throws(Exception::class)
    fun findTree(
        @PathVariable id: Long,
        @CurrentUserId userId: Long,
    ): FindTreeResponse =
        treeService.findTree(id, userId)

    @GetMapping("list")
    @Operation(summary = "트리 검색")
    @Throws(Exception::class)
    fun findTreeList(
        @RequestParam @NotBlank
        query: String,
    ): FindTreeListResponse =
        FindTreeListResponse.of(treeService.findTreeList(query.trim()))

    @GetMapping("map")
    @Operation(summary = "현재 지도에서 트리 목록 조회")
    @Throws(Exception::class)
    fun findTreeMap(
        @RequestParam topLeftLat: Float,
        @RequestParam topLeftLng: Float,
        @RequestParam bottomLeftLat: Float,
        @RequestParam bottomLeftLng: Float,
        @RequestParam topRightLat: Float,
        @RequestParam topRightLng: Float,
        @RequestParam bottomRightLat: Float,
        @RequestParam bottomRightLng: Float,
    ): FindTreeMapResponse =
        FindTreeMapResponse.of(
            treeService.findTreeMap(
                topLeftLat,
                topLeftLng,
                bottomLeftLat,
                bottomLeftLng,
                topRightLat,
                topRightLng,
                bottomRightLat,
                bottomRightLng,
            ),
        )

    @PutMapping("{id}")
    @Operation(summary = "트리 수정")
    @Throws(Exception::class)
    fun updateTree(
        @PathVariable id: Long,
        @Valid @RequestBody
        req: UpdateTreeRequest,
        @CurrentUserId userId: Long,
    ) = treeService.updateTree(
        id,
        req,
        userId,
    )

    @DeleteMapping("{id}")
    @Operation(summary = "트리 삭제")
    fun deleteTree(
        @PathVariable id: Long,
        @CurrentUserId userId: Long,
    ) =
        treeService.deleteTree(id, userId)
}
