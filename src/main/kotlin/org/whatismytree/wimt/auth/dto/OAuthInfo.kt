package org.whatismytree.wimt.auth.dto

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.whatismytree.wimt.auth.domain.OAuthType

data class OAuthInfo(
    val oAuthType: OAuthType,
    val oAuthId: String,
    val email: String,
) {
    companion object {
        fun fromToken(token: OAuth2AuthenticationToken): OAuthInfo {
            val oAuthType = oauthTypeFrom(token)
            return OAuthInfo(oAuthType, oauthIdFrom(token), emailFrom(token, oAuthType))
        }

        private fun oauthTypeFrom(token: OAuth2AuthenticationToken) =
            OAuthType.valueOf(token.authorizedClientRegistrationId.uppercase())

        private fun oauthIdFrom(token: OAuth2AuthenticationToken): String = token.name

        private fun emailFrom(token: OAuth2AuthenticationToken, oAuthType: OAuthType) =
            when (oAuthType) {
                OAuthType.GOOGLE -> token.principal.attributes["email"] as String
                OAuthType.KAKAO -> {
                    val kakaoAccount = token.principal.attributes["kakao_account"] as Map<*, *>
                    kakaoAccount["email"] as String
                }
            }
    }
}
