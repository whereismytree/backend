package org.whatismytree.wimt.tree.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
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
import org.whatismytree.wimt.tree.controller.dto.CreateTreeDto
import org.whatismytree.wimt.tree.controller.dto.FindPostedTreeListDto
import org.whatismytree.wimt.tree.controller.dto.FindSavedTreeListDto
import org.whatismytree.wimt.tree.controller.dto.FindTreeDto
import org.whatismytree.wimt.tree.controller.dto.FindTreeListDto
import org.whatismytree.wimt.tree.controller.dto.FindTreeMapDto
import org.whatismytree.wimt.tree.controller.dto.UpdateTreeDto
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
    fun findPostedTreeList(): FindPostedTreeListDto
    = treeService.findPostedTreeList(1)

    @GetMapping("saved")
    @Operation(summary = "내가 저장한 트리 목록 조회")
    @Throws(Exception::class)
    fun findSavedTreeList(): FindSavedTreeListDto
    = treeService.findSavedTreeList(1)
}
