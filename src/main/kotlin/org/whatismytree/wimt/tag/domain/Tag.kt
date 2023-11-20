package org.whatismytree.wimt.tag.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import org.whatismytree.wimt.common.BaseTimeEntity
import java.time.LocalDateTime

@Entity(name = "tag")
class Tag protected constructor(
    content: String,
) : BaseTimeEntity() {

    @Column(name = "content", nullable = false)
    var content: String = content
        protected set

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null
        protected set

    companion object {
        fun of(
            content: String,
        ): Tag {
            return Tag(
                content = content,
            )
        }
    }
}
