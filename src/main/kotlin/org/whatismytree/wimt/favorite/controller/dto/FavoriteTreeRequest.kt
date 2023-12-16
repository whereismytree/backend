package org.whatismytree.wimt.favorite.controller.dto

import jakarta.validation.constraints.Min

data class FavoriteTreeRequest(
    @field:Min(1)
    val treeId: Long,
    val isFavorite: Boolean,
)
