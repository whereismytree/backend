package org.whatismytree.wimt.user.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.whatismytree.wimt.auth.dto.OAuthInfo
import org.whatismytree.wimt.user.domain.User
import org.whatismytree.wimt.user.repository.UserRepository

@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository
) {
    @Transactional
    fun findOrCreateUser(oAuthInfo: OAuthInfo): User {
        val user = User.of(oAuthInfo.email, oAuthInfo.oAuthType, oAuthInfo.oAuthId)
        return userRepository.findUserByOAuthInfo(oAuthInfo) ?: userRepository.save(user)
    }
}