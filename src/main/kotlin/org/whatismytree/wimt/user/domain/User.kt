package org.whatismytree.wimt.user.domain

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.whatismytree.wimt.auth.domain.OAuthType
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(
    name = "users",
    uniqueConstraints = [
        UniqueConstraint(name = "uk_oauth", columnNames = ["oauthType", "oauthId"]),
    ]
)
class User(
    @Column(nullable = false, updatable = false, length = 255)
    val email: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false, length = 50)
    val oauthType: OAuthType,

    @Column(nullable = false, updatable = false, length = 255)
    val oauthId: String,

    @Column(length = 50, unique = true)
    var nickname: String? = null,

    @Column(length = 255)
    var profileImageUrl: String? = null,

    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime? = null,

    @LastModifiedDate
    @Column(nullable = false)
    var updatedAt: LocalDateTime? = null,

    var deletedAt: LocalDateTime? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
) {
    fun softDelete() {
        deletedAt = LocalDateTime.now()
    }
}