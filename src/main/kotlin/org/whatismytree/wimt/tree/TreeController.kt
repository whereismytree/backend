package org.whatismytree.wimt.tree

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.whatismytree.wimt.tree.dto.CreateTreeDto
import org.whatismytree.wimt.tree.dto.UpdateTreeDto
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "트리 관리")
@RequestMapping("/v1/tree")
class TreeController(
    private val treeService: TreeService
) {
    @PostMapping("")
    @Operation(summary = "트리 등록")
    @Throws(Exception::class)
    fun createTree(@Valid @RequestBody req: CreateTreeDto.Req)
    = treeService.createTree(req)

    @GetMapping("{id}")
    @Operation(summary = "트리 조회")
    @Throws(Exception::class)
    fun findTree(@PathVariable id: String)
    = treeService.findTree(id)

    @GetMapping("list")
    @Operation(summary = "트리 목록 조회")
    @Throws(Exception::class)
    fun findTreeList()
    = treeService.findTreeList()

    @PutMapping("{id}")
    @Operation(summary = "트리 수정")
    @Throws(Exception::class)
    fun updateTree(
        @PathVariable id: String,
        @Valid @RequestBody req: UpdateTreeDto.Req
    ) = treeService.updateTree(
        id,
        req
    )

    @DeleteMapping("{id}")
    @Operation(summary = "트리 삭제")
    @Throws(Exception::class)
    fun deleteTree(@PathVariable id: String)
    = treeService.deleteTree(id)


}