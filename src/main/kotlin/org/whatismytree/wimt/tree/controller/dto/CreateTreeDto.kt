package org.whatismytree.wimt.tree.controller.dto

import java.time.LocalDate

class CreateTreeDto {
    class Req (
        val name: String,
        val lat: Float,
        val lng: Float,
        val addressType: String,
        val roadAddress: String? = null,
        val streetAddress: String? = null,
        val detailAddress: String? = null,
        val exhibitionStartDate: LocalDate? = null,
        val exhibitionEndDate: LocalDate? = null,
        val spaceType: String? = null,
        val businessDays: String? = null,
        val isPet: Boolean? = null,
        val title: String? = null,
        val description: String? = null,
    )
}