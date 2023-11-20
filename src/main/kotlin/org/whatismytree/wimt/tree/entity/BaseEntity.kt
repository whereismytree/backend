package org.whatismytree.wimt.tree.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.Comment
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {
    @CreatedDate
    @Column(nullable = false, updatable = false)
    @Comment("생성일")
    var createdAt: LocalDateTime = LocalDateTime.MIN
        protected set

    @LastModifiedDate
    @Column(nullable = false)
    @Comment("수정일")
    var updatedAt: LocalDateTime = LocalDateTime.MIN
        protected set

    @Column()
    @Comment("삭제일")
    var deletedAt: LocalDateTime = LocalDateTime.MIN
        protected set
}
