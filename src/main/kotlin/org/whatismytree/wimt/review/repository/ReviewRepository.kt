package org.whatismytree.wimt.review.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.whatismytree.wimt.review.domain.Review

interface ReviewRepository : JpaRepository<Review, Long>
