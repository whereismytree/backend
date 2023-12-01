package org.whatismytree.wimt.review.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Embeddable
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany
import java.util.*

@Embeddable
class ReviewTags {

    @OneToMany(
        mappedBy = "review",
        orphanRemoval = true,
        cascade = [CascadeType.ALL],
        fetch = FetchType.EAGER,
        targetEntity = ReviewTag::class,
    )
    private var value: MutableList<ReviewTag> = mutableListOf()

    fun update(review: Review, tagIds: List<Long>) {
        val existedTagIds = value.map { it.tagId }.toSet()

        val newTagIds = tagIds.filterNot { it in existedTagIds }
        val deleteTagIds = existedTagIds.filterNot { it in tagIds }

        value.removeAll(value.filter { it.tagId in deleteTagIds })
        value.addAll(newTagIds.map { ReviewTag.of(review, it) })
    }

    fun getValue(): List<ReviewTag> {
        return value.toList()
    }
}
