package org.whatismytree.wimt.favorite.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table
import org.whatismytree.wimt.common.BaseTimeEntity

@Entity
@Table(
    indexes = [
        Index(name = "idx_favorite_1", columnList = "tree_id, user_id", unique = true),
        Index(name = "idx_favorite_2", columnList = "user_id"),
    ],
)
class Favorite private constructor(
    userId: Long,
    treeId: Long,
) : BaseTimeEntity() {
    @Column(name = "user_id", nullable = false)
    var userId: Long = userId
        protected set

    @Column(name = "tree_id", nullable = false)
    var treeId: Long = treeId
        protected set

    companion object {
        fun of(userId: Long, treeId: Long): Favorite {
            return Favorite(userId, treeId)
        }
    }
}
