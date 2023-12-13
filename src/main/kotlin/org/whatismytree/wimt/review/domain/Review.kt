package org.whatismytree.wimt.review.domain

import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table
import org.whatismytree.wimt.common.BaseTimeEntity
import java.time.LocalDateTime

@Entity(name = "review")
@Table(
    indexes = [
        Index(name = "idx_review_1", columnList = "tree_id"),
        Index(name = "idx_review_2", columnList = "user_id"),
    ],
)
class Review private constructor(
    treeId: Long,
    userId: Long,
    content: String,
    imageUrl: String,
) : BaseTimeEntity() {

    @Column(name = "tree_id", nullable = false)
    var treeId: Long = treeId
        protected set

    @Column(name = "user_id", nullable = false)
    var userId: Long = userId
        protected set

    @Column(name = "content", nullable = false, length = 3000)
    var content: String = content
        protected set

    @Column(name = "image_url", length = 1000)
    var imageUrl: String? = imageUrl
        protected set

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null
        protected set

    @Embedded
    var tags: ReviewTags = ReviewTags()
        protected set

    fun isAuthor(userId: Long): Boolean {
        return this.userId == userId
    }

    fun delete(now: LocalDateTime = LocalDateTime.now()) {
        deletedAt = now
    }

    fun update(content: String?, tagIds: List<Long>?, imageUrl: String?) {
        content?.let { this.content = it }
        imageUrl?.let { this.imageUrl = it }
        tagIds?.let { tags.update(this, it) }
    }

    companion object {
        fun of(
            treeId: Long,
            userId: Long,
            content: String,
            tagIds: List<Long>,
            imageUrl: String,
        ): Review {
            val review = Review(
                treeId = treeId,
                userId = userId,
                content = content,
                imageUrl = imageUrl,
            )

            review.tags.update(review, tagIds)

            return review
        }
    }
}
