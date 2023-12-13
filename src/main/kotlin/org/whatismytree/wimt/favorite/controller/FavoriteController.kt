package org.whatismytree.wimt.favorite.controller

import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.whatismytree.wimt.common.CurrentUserId
import org.whatismytree.wimt.favorite.controller.dto.FavoriteTreeRequest
import org.whatismytree.wimt.favorite.service.FavoriteService

@RestController
@Tag(name = "즐겨찾기 관리")
@RequestMapping("/v1/favorites")
class FavoriteController(
    private val favoriteService: FavoriteService,
) {

    @PostMapping("/trees")
    fun updateFavoriteTree(
        @Valid @RequestBody request: FavoriteTreeRequest,
        @CurrentUserId userId: Long,
    ) {
        favoriteService.updateFavoriteTree(userId, request.treeId, request.isFavorite)
    }
}
