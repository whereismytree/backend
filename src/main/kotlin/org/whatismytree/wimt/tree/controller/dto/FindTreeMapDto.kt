package org.whatismytree.wimt.tree.controller.dto

class FindTreeMapDto {
    class Res(
        val id: Long,
        val name: String,
        val lat: Float,
        val lng: Float,
    )
}
