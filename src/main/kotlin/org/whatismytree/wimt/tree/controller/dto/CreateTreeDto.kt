package org.whatismytree.wimt.tree.controller.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

class CreateTreeDto {
    class Req (
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
        val spaceType: String? = null,
        val businessDays: String? = null,
        val isPet: Boolean? = null,
        val title: String? = null,
        val extraInfo: String? = null,
    )
}
