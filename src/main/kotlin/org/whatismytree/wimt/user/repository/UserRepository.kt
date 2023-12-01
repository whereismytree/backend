package org.whatismytree.wimt.user.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.whatismytree.wimt.user.domain.User

interface UserRepository : JpaRepository<User, Long>, CustomUserRepository {
    fun existsByNickname(nickname: String): Boolean
}
