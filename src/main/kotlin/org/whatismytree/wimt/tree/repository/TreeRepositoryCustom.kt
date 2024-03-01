package org.whatismytree.wimt.tree.repository

import org.whatismytree.wimt.tree.controller.dto.FindPostedTreeListDto
import org.whatismytree.wimt.tree.controller.dto.FindSavedTreeListDto
import org.whatismytree.wimt.tree.repository.dto.FindTreeListResult

interface TreeRepositoryCustom {
    fun findTreeList(query: String): List<FindTreeListResult>

    fun findPostedTreeList(userId: Long): List<FindPostedTreeListDto.Res>

    fun findSavedTreeList(userId: Long): List<FindSavedTreeListDto.Res>
}
