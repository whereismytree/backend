package org.whatismytree.wimt.tree.controller.dto

data class FindPostedTreeListResponse(
    val totalTrees: Int,
    val trees: List<PostedTreeSummary>,
) {
    data class PostedTreeSummary(
        val treeId: Long,
        val name: String,
        val lat: Float,
        val lng: Float,
        val address: String,
        val reviewsCount: Long,
    )
}
