package org.whatismytree.wimt.tree.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.Comment
import org.whatismytree.wimt.common.BaseTimeEntity
import org.whatismytree.wimt.tree.controller.dto.UpdateTreeDto
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "tree")
class Tree(

    @Column(name = "user_id", nullable = false)
    @Comment("등록한 유저 ID")
    val userId: Long,

    @Column
    var name: String,

    @Column
    var imageUrl: String?,

    @Column
    var lat: Float,

    @Column
    var lng: Float,

    @Column
    var addressType: String,

    @Column
    var streetAddress: String? = null,

    @Column
    var roadAddress: String? = null,

    @Column
    var detailAddress: String? = null,

    @Column
    var space: String? = null,

    @Column
    var exhibitionStartDate: LocalDate? = null,

    @Column
    var exhibitionEndDate: LocalDate? = null,

    @Column
    var businessDays: String? = null,

    @Column
    var isPet: Boolean? = null,

    @Column
    var extraInfo: String? = null,

    @Column
    var deletedAt: LocalDateTime? = null,

) : BaseTimeEntity() {

    enum class AddressType {
        ROAD,
        STREET,
    }

    enum class Space {
        INTERIOR,
        EXTERNAL,
    }

    fun updateTree(req: UpdateTreeDto.Req) {
        this.name = req.name
        this.lat = req.lat
        this.lng = req.lng
        this.addressType = req.addressType
        this.roadAddress = req.roadAddress
        this.streetAddress = req.streetAddress
        this.detailAddress = req.detailAddress
        this.space = req.spaceType
        this.exhibitionStartDate = req.exhibitionStartDate
        this.exhibitionEndDate = req.exhibitionEndDate
        this.businessDays = req.businessDays
        this.isPet = req.isPet
        this.extraInfo = req.extraInfo
    }
}
