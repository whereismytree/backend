package org.whatismytree.wimt.tree.controller.dto

import org.whatismytree.wimt.tree.entity.Tree

data class FindTreeMapResponse(
    val trees: List<TreeMapSummary>,
) {
    data class TreeMapSummary(
        val id: Long,
        val name: String,
        val lat: Float,
        val lng: Float,
    )

    companion object {
        fun of(findTreeMap: List<Tree>): FindTreeMapResponse {
            return FindTreeMapResponse(
                findTreeMap.map {
                    TreeMapSummary(
                        id = it.id,
                        name = it.name,
                        lat = it.lat,
                        lng = it.lng,
                    )
                },
            )
        }
    }
}
