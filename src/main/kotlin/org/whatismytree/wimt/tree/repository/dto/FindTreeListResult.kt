package org.whatismytree.wimt.tree.repository.dto

data class FindTreeListResult(
    val id: Long,
    val name: String,
    val lat: Float,
    val lng: Float,
    val address: String,
    val imageUrl: String?,
)
