package org.whatismytree.wimt.user.repository

import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.whatismytree.wimt.auth.dto.OAuthInfo
import org.whatismytree.wimt.favorite.domain.QFavorite.favorite
import org.whatismytree.wimt.review.domain.QReview.review
import org.whatismytree.wimt.tree.entity.QTree.tree
import org.whatismytree.wimt.user.domain.QUser.user
import org.whatismytree.wimt.user.domain.User
import org.whatismytree.wimt.user.repository.dto.UserDetailResult

class CustomUserRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : CustomUserRepository {
    override fun findUserByOAuthInfo(oAuthInfo: OAuthInfo): User? {
        return jpaQueryFactory
            .selectFrom(user)
            .where(
                user.oauthType.eq(oAuthInfo.oAuthType),
                user.oauthId.eq(oAuthInfo.oAuthId),
            )
            .fetchOne()
    }

    override fun findUserDetailById(userId: Long): UserDetailResult? {
        val postedTreeCount = getPostedTreeCount(userId)
        val savedTreeCount = getSavedTreeCount(userId)
        val reviewCount = getReviewCount(userId)

        return jpaQueryFactory
            .select(
                Projections.constructor(
                    UserDetailResult::class.java,
                    user.nickname,
                    user.email,
                    user.oauthType,
                    user.profileImageUrl,
                    Expressions.asNumber(postedTreeCount),
                    Expressions.asNumber(savedTreeCount),
                    Expressions.asNumber(reviewCount),
                ),
            )
            .from(user)
            .where(
                user.id.eq(userId),
                user.deletedAt.isNull(),
            )
            .fetchOne()
    }

    private fun getReviewCount(userId: Long) = jpaQueryFactory
        .select(review.count())
        .from(review)
        .where(
            review.userId.eq(userId),
            review.deletedAt.isNull(),
        )
        .fetchOne() ?: 0L

    private fun getPostedTreeCount(userId: Long) = jpaQueryFactory
        .select(tree.count())
        .from(tree)
        .where(
            tree.userId.eq(userId),
            tree.deletedAt.isNull(),
        )
        .fetchOne() ?: 0L

    private fun getSavedTreeCount(userId: Long) = jpaQueryFactory
        .select(favorite.count())
        .from(favorite)
        .where(
            favorite.userId.eq(userId),
        )
        .fetchOne() ?: 0L
}
