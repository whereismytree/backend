package org.whatismytree.wimt.tree.repository

import org.whatismytree.wimt.tree.controller.dto.FindTreeListDto

interface TreeRepositoryCustom {
    fun findTreeList(
        name: String?,
        address: String?,
    ): List<FindTreeListDto.Res>
}
