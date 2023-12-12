package org.whatismytree.wimt.favorite.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.whatismytree.wimt.favorite.domain.Favorite

interface FavoriteRepository: JpaRepository<Favorite, Long> {
    fun findByUserIdAndTreeId(userId: Long, treeId: Long): Favorite?
}
