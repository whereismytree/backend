package org.whatismytree.wimt.tree.service

import org.whatismytree.wimt.tree.controller.dto.CreateTreeDto
import org.whatismytree.wimt.tree.controller.dto.UpdateTreeDto
import org.springframework.stereotype.Service
import org.whatismytree.wimt.tree.controller.dto.FindTreeDto
import org.whatismytree.wimt.tree.controller.dto.FindTreeListDto
import org.whatismytree.wimt.tree.entity.Tree
import org.whatismytree.wimt.tree.repository.TreeRepository
import java.lang.Exception

@Service
class TreeService (
    private val treeRepository: TreeRepository,
) {
    fun createTree(req: CreateTreeDto.Req) {
        val tree = Tree(
            name = req.name,
            lat = req.lat,
            lng = req.lng,
            addressType = Tree.AddressType.valueOf(req.addressType),
            streetAddress = req.streetAddress,
            roadAddress = req.roadAddress,
            detailAddress = req.detailAddress,
            space = req.spaceType?.let { Tree.Space.valueOf(it) },
            exhibitionStartDate = req.exhibitionStartDate,
            exhibitionEndDate = req.exhibitionEndDate,
            businessDays = req.businessDays,
            isPet = req.isPet,
            extraInfo = req.extraInfo
        )

        treeRepository.save(tree)
    }

    fun findTree(id: Long): FindTreeDto.Res {
        val tree = treeRepository.findByIdAndDeletedAtIsNull(id)
            ?: throw Exception("id로 조회되는 tree가 없습니다.")

        return FindTreeDto.Res (
            name = tree.name,
            lat = tree.lat,
            lng = tree.lng,
            addressType = tree.addressType.name,
            roadAddress = tree.roadAddress,
            streetAddress = tree.streetAddress,
            detailAddress = tree.detailAddress,
            exhibitionStartDate = tree.exhibitionStartDate,
            exhibitionEndDate = tree.exhibitionEndDate,
            spaceType = tree.space?.name,
            businessDays = tree.businessDays,
            isPet = tree.isPet,
            extraInfo = tree.extraInfo,
        )
    }

    fun findTreeList(
        topLeftLat: Float,
        topLeftLng: Float,
        bottomLeftLat: Float,
        bottomLeftLng: Float,
        topRightLat: Float,
        topRightLng: Float,
        bottomRightLat: Float,
        bottomRightLng: Float,
    ): List<FindTreeListDto.Res> {
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

        if (treeList.isEmpty()) {
            throw Exception("범위 내에 조회되는 tree가 없습니다.")
        }

        return treeList.map {
            FindTreeListDto.Res (
                id = it.id,
                name = it.name,
                lat = it.lat,
                lng = it.lng,
            )
        }
    }

    fun updateTree(
        id: Long,
        req: UpdateTreeDto.Req
    ) {
        val tree = treeRepository.findByIdAndDeletedAtIsNull(id)
            ?: throw Exception("id로 조회되는 tree가 없습니다.")
        tree.updateTree(req)
    }

    fun deleteTree(id: Long) {
        treeRepository.findByIdAndDeletedAtIsNull(id)
            ?: throw Exception("id로 조회되는 tree가 없습니다.")
        treeRepository.deleteById(id)
    }
}