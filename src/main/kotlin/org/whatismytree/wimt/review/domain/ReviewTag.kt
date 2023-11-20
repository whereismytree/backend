package org.whatismytree.wimt.review.domain

import jakarta.persistence.Column
import jakarta.persistence.ConstraintMode
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.ForeignKey
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity(name = "review_tag")
@EntityListeners(AuditingEntityListener::class)
@IdClass(ReviewTagId::class)
class ReviewTag private constructor(
    review: Review,
    tagId: Long,
) {

    @Id
    @Column(name = "tag_id", nullable = false)
    val tagId: Long = tagId

    @Id
    @ManyToOne
    @JoinColumn(
        name = "review_id",
        nullable = false,
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT),
    )
    val review: Review = review

    @CreatedDate
    lateinit var createdAt: LocalDateTime
        protected set

    companion object {
        fun of(
            review: Review,
            tagId: Long,
        ): ReviewTag {
            return ReviewTag(
                review = review,
                tagId = tagId,
            )
        }
    }
}
