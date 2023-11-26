package org.whatismytree.wimt.user.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.whatismytree.wimt.auth.dto.OAuthInfo
import org.whatismytree.wimt.user.domain.QUser.user
import org.whatismytree.wimt.user.domain.User

class CustomUserRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
): CustomUserRepository {
    override fun findUserByOAuthInfo(oAuthInfo: OAuthInfo): User? {
        return jpaQueryFactory
            .selectFrom(user)
            .where(
                user.oauthType.eq(oAuthInfo.oAuthType),
                user.oauthId.eq(oAuthInfo.oAuthId)
            )
            .fetchOne()
    }
}