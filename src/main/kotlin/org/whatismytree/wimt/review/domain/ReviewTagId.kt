package org.whatismytree.wimt.review.domain

import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class ReviewTagId(
    private val tagId: Long,
    private val review: Long
) : Serializable
