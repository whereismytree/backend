package org.whatismytree.wimt.tree.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.whatismytree.wimt.auth.handler.JwtTokenProvider
import org.whatismytree.wimt.tree.controller.dto.CreateTreeDto
import org.whatismytree.wimt.tree.controller.dto.FindPostedTreeListDto
import org.whatismytree.wimt.tree.controller.dto.FindSavedTreeListDto
import org.whatismytree.wimt.tree.controller.dto.FindTreeDto
import org.whatismytree.wimt.tree.controller.dto.FindTreeListDto
import org.whatismytree.wimt.tree.controller.dto.FindTreeMapDto
import org.whatismytree.wimt.tree.controller.dto.UpdateTreeDto
import org.whatismytree.wimt.tree.entity.Tree
import org.whatismytree.wimt.tree.repository.TreeRepository
import org.whatismytree.wimt.user.repository.UserRepository
import java.lang.Exception
import java.time.LocalDateTime

@Service
class TreeService(
    private val treeRepository: TreeRepository,
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider,
) {
    fun createTree(req: CreateTreeDto.Req) {
        val user = userRepository.findByIdOrNull(1L)
            ?: throw Exception("유저가 존재하지 않습니다.")

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

    fun findTree(id: Long): FindTreeDto.Res {
        val tree = treeRepository.findByIdAndDeletedAtIsNull(id)
            ?: throw Exception("id로 조회되는 tree가 없습니다.")

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
        )
    }

    fun findTreeList(
        name: String?,
        address: String?,
    ): List<FindTreeListDto.Res> {
        return treeRepository.findTreeList(
            name,
            address,
        )
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

    fun findPostedTreeList(userId: Long): List<FindPostedTreeListDto.Res> {
        return treeRepository.findPostedTreeList(userId)
    }

    fun findSavedTreeList(userId: Long): List<FindSavedTreeListDto.Res> {
        return treeRepository.findSavedTreeList(userId)
    }

    @Transactional
    fun updateTree(
        id: Long,
        req: UpdateTreeDto.Req,
    ) {
        val tree = treeRepository.findByIdAndDeletedAtIsNull(id)
            ?: throw Exception("id로 조회되는 tree가 없습니다.")
        tree.updateTree(req)
    }

    @Transactional
    fun deleteTree(id: Long) {
        val tree = treeRepository.findByIdOrNull(id)
            ?: throw Exception("id로 조회되는 tree가 없습니다.")

        tree.deletedAt = LocalDateTime.now()
    }
}
