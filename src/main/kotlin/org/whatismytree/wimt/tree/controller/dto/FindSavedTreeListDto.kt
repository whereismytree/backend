package org.whatismytree.wimt.tree.controller.dto

class FindSavedTreeListDto {
    class Res(
        val treeId: Long,
        val name: String,
        val lat: Float,
        val lng: Float,
        val address: String,
        val reviewsCount: Long,
    )
}
