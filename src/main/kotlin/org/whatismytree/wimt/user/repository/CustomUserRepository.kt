package org.whatismytree.wimt.user.repository

import org.whatismytree.wimt.auth.dto.OAuthInfo
import org.whatismytree.wimt.user.domain.User

interface CustomUserRepository {
    fun findUserByOAuthInfo(oAuthInfo: OAuthInfo): User?
}
