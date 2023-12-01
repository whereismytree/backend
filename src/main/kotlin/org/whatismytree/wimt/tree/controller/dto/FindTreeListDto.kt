package org.whatismytree.wimt.tree.controller.dto

class FindTreeListDto {
    class Res(
        val id: Long,
        val name: String,
        val lat: Float,
        val lng: Float,
        val address: String,
        val imageUrl: String?,
    )
}
