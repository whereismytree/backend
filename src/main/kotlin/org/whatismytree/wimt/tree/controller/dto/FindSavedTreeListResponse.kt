package org.whatismytree.wimt.tree.controller.dto

data class FindSavedTreeListResponse(
    val totalTrees: Int,
    val trees: List<SavedTreeSummary>,
) {
    data class SavedTreeSummary(
        val treeId: Long,
        val name: String,
        val lat: Float,
        val lng: Float,
        val address: String,
        val reviewsCount: Long,
    )
}
