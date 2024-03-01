package org.whatismytree.wimt.tree.entity

import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.Comment
import org.whatismytree.wimt.common.BaseTimeEntity
import org.whatismytree.wimt.tree.controller.dto.UpdateTreeRequest
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "tree")
class Tree(

    @Column(name = "user_id", nullable = false)
    @Comment("등록한 유저 ID")
    val userId: Long,

    var name: String,

    var imageUrl: String?,

    var lat: Float,

    var lng: Float,

    var addressType: String,

    var streetAddress: String? = null,

    var roadAddress: String? = null,

    var detailAddress: String? = null,

    @Convert(converter = SpaceTypeConverter::class)
    var spaceType: SpaceType = SpaceType.UNKNOWN,

    var exhibitionStartDate: LocalDate? = null,

    var exhibitionEndDate: LocalDate? = null,

    var businessDays: String? = null,

    var isPet: Boolean? = null,

    var extraInfo: String? = null,

    var deletedAt: LocalDateTime? = null,
) : BaseTimeEntity() {

    enum class AddressType {
        ROAD,
        STREET,
    }

    fun updateTree(req: UpdateTreeRequest) {
        this.name = req.name
        this.lat = req.lat
        this.lng = req.lng
        this.addressType = req.addressType
        this.roadAddress = req.roadAddress
        this.streetAddress = req.streetAddress
        this.detailAddress = req.detailAddress
        this.spaceType = req.spaceType ?: SpaceType.UNKNOWN
        this.exhibitionStartDate = req.exhibitionStartDate
        this.exhibitionEndDate = req.exhibitionEndDate
        this.businessDays = req.businessDays
        this.isPet = req.isPet
        this.extraInfo = req.extraInfo
    }
}
