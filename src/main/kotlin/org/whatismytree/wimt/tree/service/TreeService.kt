package org.whatismytree.wimt.tree.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.whatismytree.wimt.favorite.repository.FavoriteRepository
import org.whatismytree.wimt.tree.controller.dto.CreateTreeDto
import org.whatismytree.wimt.tree.controller.dto.FindPostedTreeListDto
import org.whatismytree.wimt.tree.controller.dto.FindSavedTreeListDto
import org.whatismytree.wimt.tree.controller.dto.FindTreeDto
import org.whatismytree.wimt.tree.controller.dto.FindTreeMapDto
import org.whatismytree.wimt.tree.controller.dto.UpdateTreeDto
import org.whatismytree.wimt.tree.entity.Tree
import org.whatismytree.wimt.tree.repository.TreeRepository
import org.whatismytree.wimt.tree.repository.dto.FindTreeListResult
import org.whatismytree.wimt.user.repository.UserRepository
import java.lang.Exception
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class TreeService(
    private val treeRepository: TreeRepository,
    private val userRepository: UserRepository,
    private val favoriteRepository: FavoriteRepository,
) {
    @Transactional
    fun createTree(req: CreateTreeDto.Req, userId: Long) {
        val user = userRepository.findByIdOrNull(userId)
            ?: throw Exception("유저가 존재하지 않습니다.")

        when (req.addressType) {
            "STREET" -> {
                requireNotNull(req.streetAddress)
                require(req.streetAddress.isNotBlank())
            }
            "ROAD" -> {
                requireNotNull(req.roadAddress)
                require(req.roadAddress.isNotBlank())
            }
            else -> throw IllegalArgumentException("addressType이 잘못된 값입니다.")
        }

        val tree = Tree(
            userId = user.id,
            name = req.name,
            imageUrl = req.imageUrl,
            lat = req.lat,
            lng = req.lng,
            addressType = req.addressType,
            streetAddress = req.streetAddress,
            roadAddress = req.roadAddress,
            detailAddress = req.detailAddress,
            space = req.spaceType,
            exhibitionStartDate = req.exhibitionStartDate,
            exhibitionEndDate = req.exhibitionEndDate,
            businessDays = req.businessDays,
            isPet = req.isPet,
            extraInfo = req.extraInfo,
        )

        treeRepository.save(tree)
    }

    fun findTree(
        id: Long,
        userId: Long,
    ): FindTreeDto.Res {
        val tree = treeRepository.findByIdAndDeletedAtIsNull(id)
            ?: throw Exception("id로 조회되는 tree가 없습니다.")

        val isFavorite = favoriteRepository.existsByUserIdAndTreeId(
            userId = userId,
            treeId = id,
        )

        return FindTreeDto.Res(
            name = tree.name,
            lat = tree.lat,
            lng = tree.lng,
            addressType = tree.addressType,
            roadAddress = tree.roadAddress,
            streetAddress = tree.streetAddress,
            detailAddress = tree.detailAddress,
            exhibitionStartDate = tree.exhibitionStartDate,
            exhibitionEndDate = tree.exhibitionEndDate,
            spaceType = tree.space,
            businessDays = tree.businessDays,
            isPet = tree.isPet,
            extraInfo = tree.extraInfo,
            isFavorite = isFavorite,
        )
    }

    fun findTreeList(query: String): List<FindTreeListResult> {
        return treeRepository.findTreeList(query)
    }

    fun findTreeMap(
        topLeftLat: Float,
        topLeftLng: Float,
        bottomLeftLat: Float,
        bottomLeftLng: Float,
        topRightLat: Float,
        topRightLng: Float,
        bottomRightLat: Float,
        bottomRightLng: Float,
    ): List<FindTreeMapDto.Res> {
        val treeList = treeRepository.findTreesWithinRectangle(
            topLeftLat,
            topLeftLng,
            bottomLeftLat,
            bottomLeftLng,
            topRightLat,
            topRightLng,
            bottomRightLat,
            bottomRightLng,
        )

        return treeList.map {
            FindTreeMapDto.Res(
                id = it.id,
                name = it.name,
                lat = it.lat,
                lng = it.lng,
            )
        }
    }

    fun findPostedTreeList(userId: Long): FindPostedTreeListDto {
        val res = treeRepository.findPostedTreeList(userId)
        return FindPostedTreeListDto(
            totalTrees = res.size,
            trees = res,
        )
    }

    fun findSavedTreeList(userId: Long): FindSavedTreeListDto {
        val res = treeRepository.findSavedTreeList(userId)
        return FindSavedTreeListDto(
            totalTrees = res.size,
            trees = res,
        )
    }

    @Transactional
    fun updateTree(
        id: Long,
        req: UpdateTreeDto.Req,
        userId: Long,
    ) {
        val tree = treeRepository.findByIdAndDeletedAtIsNull(id)
            ?: throw Exception("id로 조회되는 tree가 없습니다.")

        require(tree.userId == userId) { "본인이 생성하지 않은 트리는 수정할 수 없습니다." }

        tree.updateTree(req)
    }

    @Transactional
    fun deleteTree(id: Long, userId: Long) {
        val tree = treeRepository.findByIdOrNull(id)
            ?: throw Exception("id로 조회되는 tree가 없습니다.")

        require(tree.userId == userId) { "본인이 생성하지 않은 트리는 삭제할 수 없습니다." }

        tree.deletedAt = LocalDateTime.now()
    }
}
