package org.whatismytree.wimt.tag.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import org.whatismytree.wimt.common.BaseTimeEntity
import java.time.LocalDateTime

@Entity(name = "tag")
class Tag protected constructor(
    contents: String,
) : BaseTimeEntity() {

    @Column(name = "contents", nullable = false)
    var contents: String = contents
        protected set

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null
        protected set

    companion object {
        fun of(
            contents: String,
        ): Tag {
            return Tag(
                contents = contents,
            )
        }
    }
}
