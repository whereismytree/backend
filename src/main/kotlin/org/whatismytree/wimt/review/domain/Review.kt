package org.whatismytree.wimt.review.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ConstraintMode
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.Index
import jakarta.persistence.MapKeyColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.whatismytree.wimt.common.BaseTimeEntity
import java.time.LocalDateTime

@Entity(name = "review")
@Table(
    indexes = [
        Index(name = "idx_review_1", columnList = "tree_id"),
        Index(name = "idx_review_2", columnList = "user_id"),
    ]
)
class Review protected constructor(
    treeId: Long,
    userId: Long,
    content: String,
    imageUrl: String?,
) : BaseTimeEntity() {

    @Column(name = "tree_id", nullable = false)
    var treeId: Long = treeId
        protected set

    @Column(name = "user_id", nullable = false)
    var userId: Long = userId
        protected set

    @Column(name = "content", nullable = false, length = 3000)
    var contents: String = content
        protected set

    @Column(name = "image_url", length = 1000)
    var imageUrl: String? = imageUrl
        protected set

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null
        protected set

    @OneToMany(
        mappedBy = "review",
        orphanRemoval = true,
        cascade = [CascadeType.ALL],
        fetch = FetchType.EAGER,
        targetEntity = ReviewTag::class
    )
    var tags: List<ReviewTag> = listOf()


    fun addTags(tagIds: List<Long>) {
        tagIds.filter { tagId -> tags.all { tag -> tag.tagId != tagId } }.forEach {
            tags += (ReviewTag.of(this, it))
        }
    }

    companion object {
        fun of(
            treeId: Long,
            userId: Long,
            content: String,
            tagIds: List<Long>,
            imageUrl: String? = null,
        ): Review {
            val review = Review(
                treeId = treeId,
                userId = userId,
                content = content,
                imageUrl = imageUrl,
            )

            review.addTags(tagIds)

            return review
        }
    }
}
