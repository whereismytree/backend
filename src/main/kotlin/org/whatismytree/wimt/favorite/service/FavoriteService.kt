package org.whatismytree.wimt.favorite.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.whatismytree.wimt.favorite.domain.Favorite
import org.whatismytree.wimt.favorite.repository.FavoriteRepository
import org.whatismytree.wimt.tree.exception.TreeNotFoundException
import org.whatismytree.wimt.tree.repository.TreeRepository

@Service
@Transactional(readOnly = true)
class FavoriteService(
    private val treeRepository: TreeRepository,
    private val favoriteRepository: FavoriteRepository,
) {
    @Transactional
    fun updateFavoriteTree(userId: Long, treeId: Long, isFavorite: Boolean) {
        treeRepository.findByIdAndDeletedAtIsNull(treeId)
            ?: throw TreeNotFoundException("해당하는 트리가 존재하지 않습니다 treeId = $treeId")

        val favorite = favoriteRepository.findByUserIdAndTreeId(userId, treeId)

        if (isFavorite && favorite == null) {
            favoriteRepository.save(Favorite.of(userId, treeId))
            return
        }

        if (!isFavorite && favorite != null) {
            favoriteRepository.delete(favorite)
            return
        }
    }
}
