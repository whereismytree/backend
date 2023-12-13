package org.whatismytree.wimt.tree.controller.dto

class FindPostedTreeListDto(
    val totalTrees: Int,
    val trees: List<Res>,
) {
    class Res(
        val treeId: Long,
        val name: String,
        val lat: Float,
        val lng: Float,
        val address: String,
        val reviewsCount: Long,
    )
}
