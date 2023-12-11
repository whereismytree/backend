package org.whatismytree.wimt.tree.repository

import org.whatismytree.wimt.tree.controller.dto.FindPostedTreeListDto
import org.whatismytree.wimt.tree.controller.dto.FindSavedTreeListDto
import org.whatismytree.wimt.tree.controller.dto.FindTreeListDto

interface TreeRepositoryCustom {
    fun findTreeList(
        name: String?,
        address: String?,
    ): List<FindTreeListDto.Res>

    fun findPostedTreeList(userId: Long): List<FindPostedTreeListDto.Res>

    fun findSavedTreeList(userId: Long): List<FindSavedTreeListDto.Res>
}
