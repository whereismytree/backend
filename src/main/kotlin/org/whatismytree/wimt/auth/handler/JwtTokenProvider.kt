package org.whatismytree.wimt.auth.handler

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.*

@Component
class JwtTokenProvider {
    @Value("\${jwt.secret}")
    private lateinit var secretKey: String

    private val key by lazy { Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)) }

    /**
     * Access Token 생성
     */
    fun createAccessToken(authentication: Authentication): String {
        val now = Date()
        val accessExpiration = Date(now.time + EXPIRATION_MILLISECONDS)

        return Jwts
            .builder()
            .setSubject(authentication.name)
            .setIssuedAt(now)
            .setExpiration(accessExpiration)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    companion object {
        private val EXPIRATION_MILLISECONDS = Duration.ofHours(1).toMillis()
    }
}
