package org.whatismytree.wimt.user.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.whatismytree.wimt.auth.dto.OAuthInfo
import org.whatismytree.wimt.user.domain.User
import org.whatismytree.wimt.user.exception.DuplicatedNicknameException
import org.whatismytree.wimt.user.exception.UserNotFoundException
import org.whatismytree.wimt.user.repository.UserRepository

@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository,
) {
    @Transactional
    fun findOrCreateUser(oAuthInfo: OAuthInfo): User {
        val user = User.of(oAuthInfo.email, oAuthInfo.oAuthType, oAuthInfo.oAuthId)
        return userRepository.findUserByOAuthInfo(oAuthInfo) ?: userRepository.save(user)
    }

    @Transactional
    fun createProfile(userId: Long, nickname: String, profileImageUrl: String) {
        val user = userRepository.findById(userId)
            .orElseThrow { throw UserNotFoundException("존재하지 않는 유저입니다. userId: $userId") }

        // 닉네임 중복 검사
        if (userRepository.existsByNickname(nickname)) {
            throw DuplicatedNicknameException("이미 존재하는 닉네임입니다. nickname: $nickname")
        }

        user.updateProfile(nickname, profileImageUrl)
    }
}
