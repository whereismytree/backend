package org.whatismytree.wimt.tree.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.whatismytree.wimt.common.CurrentUserId
import org.whatismytree.wimt.tree.controller.dto.FindPostedTreeListResponse
import org.whatismytree.wimt.tree.controller.dto.FindSavedTreeListResponse
import org.whatismytree.wimt.tree.service.TreeService

@RestController
@Tag(name = "나와 관련된 트리 관리")
@RequestMapping("/v1/my/trees")
class MyTreeController(
    private val treeService: TreeService,
) {
    @GetMapping("posted")
    @Operation(summary = "내가 등록한 트리 목록 조회")
    @Throws(Exception::class)
    fun findPostedTreeList(@CurrentUserId userId: Long): FindPostedTreeListResponse =
        treeService.findPostedTreeList(userId)

    @GetMapping("saved")
    @Operation(summary = "내가 저장한 트리 목록 조회")
    @Throws(Exception::class)
    fun findSavedTreeList(@CurrentUserId userId: Long): FindSavedTreeListResponse =
        treeService.findSavedTreeList(userId)
}
