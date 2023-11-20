package org.whatismytree.wimt.tree.entity

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "tree")
class Tree (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    val id: Long,

    @Column
    val name: String,

    @Column
    val lat: Float,

    @Column
    val lng: Float,

    @Column
    val addressType: AddressType,

    @Column
    val streetAddress: String? = null,

    @Column
    val roadAddress: String? = null,

    @Column
    val detailAddress: String? = null,

    @Column
    val space: SPACE? = null,

    @Column
    val exhibitionStartDate: LocalDate? = null,

    @Column
    val exhibitionEndDate: LocalDate? = null,

    @Column
    val businessDays: String? = null,

    @Column
    val isPet: Boolean? = null,

    @Column
    val title: String? = null,

    @Column
    val description: Boolean? = null,
): BaseEntity() {

    enum class AddressType {
        ROAD,
        STREET
    }

    enum class SPACE {
        INTERIOR,
        EXTERNAL
    }
}