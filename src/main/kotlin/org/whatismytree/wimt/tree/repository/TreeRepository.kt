package org.whatismytree.wimt.tree.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.whatismytree.wimt.tree.entity.Tree

interface TreeRepository : JpaRepository<Tree, Long>, TreeRepositoryCustom {
    fun findByIdAndDeletedAtIsNull(id: Long): Tree?

    @Query(
        "SELECT t FROM Tree t " +
            "WHERE (t.deletedAt IS NULL)" +
            "AND (t.lat BETWEEN :bottomLeftLat AND :topLeftLat)" +
            "AND (t.lng BETWEEN :bottomLeftLng AND :bottomRightLng) " +
            "AND t.lat BETWEEN :bottomRightLat AND :topRightLat " +
            "AND t.lng BETWEEN :topLeftLng AND :topRightLng",
    )
    fun findTreesWithinRectangle(
        topLeftLat: Float,
        topLeftLng: Float,
        bottomLeftLat: Float,
        bottomLeftLng: Float,
        topRightLat: Float,
        topRightLng: Float,
        bottomRightLat: Float,
        bottomRightLng: Float,
    ): List<Tree>
}
