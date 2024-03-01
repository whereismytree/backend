package org.whatismytree.wimt.tree.controller.dto

import org.whatismytree.wimt.tree.repository.dto.FindTreeListResult

data class FindTreeListResponse(
    val trees: List<FindTreeListResult>,
) {

    companion object {
        fun of(results: List<FindTreeListResult>): FindTreeListResponse {
            return FindTreeListResponse(results)
        }
    }
}
