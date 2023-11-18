package org.whatismytree.wimt.auth.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.whatismytree.wimt.auth.dto.OAuthInfo

@Component
class OAuth2AuthenticationSuccessHandler : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?,
    ) {
        val token = OAuth2AuthenticationToken::class.java.cast(authentication)

        val oAuthInfo = OAuthInfo.fromToken(token)

        println(oAuthInfo)
        // TODO: oAuthInfo를 이용해 회원 정보 조회 후, 없으면 회원 가입, 있으면 로그인 처리
    }
}