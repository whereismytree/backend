package org.whatismytree.wimt.tree.repository

import org.whatismytree.wimt.tree.controller.dto.FindPostedTreeListResponse
import org.whatismytree.wimt.tree.controller.dto.FindSavedTreeListResponse
import org.whatismytree.wimt.tree.repository.dto.FindTreeListResult

interface TreeRepositoryCustom {
    fun findTreeList(query: String): List<FindTreeListResult>

    fun findPostedTreeList(userId: Long): List<FindPostedTreeListResponse.PostedTreeSummary>

    fun findSavedTreeList(userId: Long): List<FindSavedTreeListResponse.SavedTreeSummary>
}
