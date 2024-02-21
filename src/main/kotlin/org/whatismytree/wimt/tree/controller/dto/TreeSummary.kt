package org.whatismytree.wimt.tree.controller.dto

import org.whatismytree.wimt.tree.entity.Tree

data class TreeSummary(
    val treeId: Long,
    val treeName: String,
    val address: String,
) {
    companion object {
        fun of(tree: Tree): TreeSummary {
            return TreeSummary(
                treeId = tree.id,
                treeName = tree.name,
                address = when (tree.addressType) {
                    "ROAD" -> tree.roadAddress!!
                    "STREET" -> tree.streetAddress!!
                    else -> ""
                },
            )
        }
    }
}
