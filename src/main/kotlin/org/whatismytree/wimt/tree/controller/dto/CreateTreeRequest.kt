package org.whatismytree.wimt.tree.controller.dto

import com.fasterxml.jackson.annotation.JsonFormat
import org.whatismytree.wimt.tree.entity.SpaceType
import java.time.LocalDate

data class CreateTreeRequest(
    val name: String,
    val imageUrl: String?,
    val lat: Float,
    val lng: Float,
    val addressType: String,
    val roadAddress: String? = null,
    val streetAddress: String? = null,
    val detailAddress: String? = null,
    @JsonFormat(pattern = "yyyy-MM-dd")
    val exhibitionStartDate: LocalDate? = null,
    @JsonFormat(pattern = "yyyy-MM-dd")
    val exhibitionEndDate: LocalDate? = null,
    val spaceType: SpaceType? = null,
    val businessDays: String? = null,
    val isPet: Boolean? = null,
    val extraInfo: String? = null,
)
