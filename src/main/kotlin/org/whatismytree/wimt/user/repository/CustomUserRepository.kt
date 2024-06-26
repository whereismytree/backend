package org.whatismytree.wimt.user.repository

import org.whatismytree.wimt.auth.dto.OAuthInfo
import org.whatismytree.wimt.user.domain.User
import org.whatismytree.wimt.user.repository.dto.UserDetailResult

interface CustomUserRepository {
    fun findUserByOAuthInfo(oAuthInfo: OAuthInfo): User?

    fun findUserDetailById(userId: Long): UserDetailResult?
}
