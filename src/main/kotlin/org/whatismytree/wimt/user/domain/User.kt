package org.whatismytree.wimt.user.domain

import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Column
import jakarta.persistence.UniqueConstraint
import jakarta.persistence.Convert
import org.whatismytree.wimt.auth.domain.OAuthType
import org.whatismytree.wimt.auth.domain.converter.OAuthTypeConverter
import org.whatismytree.wimt.common.BaseTimeEntity
import java.time.LocalDateTime

@Entity
@Table(
    name = "users",
    uniqueConstraints = [
        UniqueConstraint(name = "uk_oauth", columnNames = ["oauth_type", "oauth_id"]),
    ]
)
class User private constructor(
    email: String,
    oauthType: OAuthType,
    oauthId: String,
    nickname: String?,
    profileImageUrl: String?
) : BaseTimeEntity() {

    @Column(name = "email", nullable = false, updatable = false, length = 255)
    val email: String = email

    @Convert(converter = OAuthTypeConverter::class)
    @Column(name = "oauth_type", nullable = false, updatable = false, length = 50)
    val oauthType: OAuthType = oauthType

    @Column(name = "oauth_id", nullable = false, updatable = false, length = 255)
    val oauthId: String = oauthId

    @Column(name = "nickname", length = 50, unique = true)
    var nickname: String? = nickname

    @Column(name = "profile_image_url", length = 255)
    var profileImageUrl: String? = profileImageUrl

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null
        protected set

    fun softDelete() {
        deletedAt = LocalDateTime.now()
    }

    companion object {
        fun of(
            email: String,
            oauthType: OAuthType,
            oauthId: String,
            nickname: String? = null,
            profileImageUrl: String? = null
        ): User =
            User(
                email = email,
                oauthType = oauthType,
                oauthId = oauthId,
                nickname = nickname,
                profileImageUrl = profileImageUrl
            )
    }
}
