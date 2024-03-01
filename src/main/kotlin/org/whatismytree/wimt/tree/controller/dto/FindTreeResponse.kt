package org.whatismytree.wimt.tree.controller.dto

import org.whatismytree.wimt.tree.entity.SpaceType
import java.time.LocalDate

data class FindTreeResponse(
    val name: String,
    val lat: Float,
    val lng: Float,
    val addressType: String,
    val roadAddress: String? = null,
    val streetAddress: String? = null,
    val detailAddress: String? = null,
    val exhibitionStartDate: LocalDate? = null,
    val exhibitionEndDate: LocalDate? = null,
    val spaceType: SpaceType,
    val businessDays: String? = null,
    val isPet: Boolean? = null,
    val extraInfo: String? = null,
    val isFavorite: Boolean,
)
