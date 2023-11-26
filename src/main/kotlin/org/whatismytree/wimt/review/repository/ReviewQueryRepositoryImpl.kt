package org.whatismytree.wimt.review.repository

import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import org.whatismytree.wimt.review.domain.QReview.review
import org.whatismytree.wimt.review.domain.QReviewTag.reviewTag
import org.whatismytree.wimt.review.repository.dto.ReviewDetailResult
import org.whatismytree.wimt.review.repository.dto.ReviewImageResult
import org.whatismytree.wimt.tag.domain.QTag.tag
import org.whatismytree.wimt.user.domain.QUser.user

@Repository
class ReviewQueryRepositoryImpl(
    private val query: JPAQueryFactory,
) : ReviewQueryRepository {
    override fun findAllByTreeId(treeId: Long): List<ReviewDetailResult> =
        query
            .select(
                Projections.constructor(
                    ReviewDetailResult::class.java,
                    review.id,
                    review.userId,
                    user.nickname,
                    user.profileImageUrl,
                    review.createdAt,
                    review.imageUrl,
                    review.content,
                    Expressions.stringTemplate("GROUP_CONCAT({0})", tag.content),
                ),
            )
            .from(review)
            .join(review.tags.value, reviewTag)
            .innerJoin(tag).on(reviewTag.tagId.eq(tag.id))
            .innerJoin(user).on(review.userId.eq(user.id))
            .where(
                review.treeId.eq(treeId),
                review.deletedAt.isNull(),
            )
            .groupBy(review.id)
            .fetch()

    override fun findAllImagesByTreeId(treeId: Long): List<ReviewImageResult> =
        query
            .select(
                Projections.constructor(
                    ReviewImageResult::class.java,
                    review.id,
                    review.imageUrl,
                ),
            )
            .from(review)
            .where(
                review.treeId.eq(treeId),
                review.imageUrl.isNotNull,
                review.deletedAt.isNull(),
            )
            .fetch()

    override fun findById(reviewId: Long): ReviewDetailResult? =
        query
            .select(
                Projections.constructor(
                    ReviewDetailResult::class.java,
                    review.id,
                    review.userId,
                    user.nickname,
                    user.profileImageUrl,
                    review.createdAt,
                    review.imageUrl,
                    review.content,
                    Expressions.stringTemplate("GROUP_CONCAT({0})", tag.content),
                ),
            )
            .from(review)
            .join(review.tags.value, reviewTag)
            .innerJoin(tag).on(reviewTag.tagId.eq(tag.id))
            .innerJoin(user).on(review.userId.eq(user.id))
            .where(
                review.id.eq(reviewId),
                review.deletedAt.isNull(),
            )
            .groupBy(review.id)
            .fetchOne()
}
