package org.whatismytree.wimt.tree.controller.dto

class FindPostedTreeListDto {
    class Res(
        val treeId: Long,
        val name: String,
        val lat: Float,
        val lng: Float,
        val address: String,
        val reviewsCount: Long,
    )
}
