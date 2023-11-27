package org.whatismytree.wimt.auth.handler

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
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
    fun createAccessToken(authentication: Authentication, userId: Long): String {
        val now = Date()
        val accessExpiration = Date(now.time + EXPIRATION_MILLISECONDS)

        return Jwts
            .builder()
            .setClaims(mapOf(OAUTH_TYPE to extractOAuthType(authentication)))
            .setSubject(userId.toString())
            .setIssuedAt(now)
            .setExpiration(accessExpiration)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    private fun extractOAuthType(authentication: Authentication): String {
        val oAuth2AuthenticationToken = authentication as OAuth2AuthenticationToken
        return oAuth2AuthenticationToken.authorizedClientRegistrationId
    }

    /**
     * Access Token으로부터 OAuth2AuthenticationToken 생성
     */
    fun getAuthentication(token: String): Authentication {
        val claims = getClaims(token)

        val defaultOAuth2User = DefaultOAuth2User(
            listOf(),
            mapOf(USER_ID to claims.subject.toLong(), OAUTH_TYPE to claims[OAUTH_TYPE] as String),
            USER_ID,
        )

        return OAuth2AuthenticationToken(
            defaultOAuth2User,
            defaultOAuth2User.authorities,
            claims[OAUTH_TYPE] as String,
        )
    }

    /**
     * Token 검증
     */
    fun isValidToken(token: String): Boolean {
        return try {
            getClaims(token)
            true
        } catch (e: JwtException) {
            println(e.message)
            false
        } catch (e: IllegalArgumentException) {
            println(e.message)
            false
        }
    }

    private fun getClaims(token: String): Claims =
        Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body

    companion object {
        private const val USER_ID = "userId"
        private const val OAUTH_TYPE = "oauthType"
        private val EXPIRATION_MILLISECONDS = Duration.ofHours(1).toMillis()
    }
}
