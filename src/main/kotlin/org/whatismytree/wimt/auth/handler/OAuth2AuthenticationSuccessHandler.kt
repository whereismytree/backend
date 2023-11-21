package org.whatismytree.wimt.auth.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.whatismytree.wimt.auth.dto.OAuthInfo
import org.whatismytree.wimt.user.service.UserService

@Component
class OAuth2AuthenticationSuccessHandler(
    private val userService: UserService,
    private val jwtTokenProvider: JwtTokenProvider,
) : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?,
    ) {
        val token = OAuth2AuthenticationToken::class.java.cast(authentication)

        val oAuthInfo = OAuthInfo.fromToken(token)

        val findUser = userService.findOrCreateUser(oAuthInfo)
        // TODO : 회원 닉네임 설정 여부에 따라 분기 처리
        val accessToken = jwtTokenProvider.createAccessToken(authentication!!)

        val redirectUri = getRedirectUri(request)

        response?.sendRedirect("$redirectUri?access-token=$accessToken")
    }

    private fun getRedirectUri(request: HttpServletRequest?) =
        request?.getParameter(REDIRECT_URI) ?: DEFAULT_OAUTH_REDIRECT_URI

    companion object {
        private const val REDIRECT_URI = "redirect_uri"
        private const val DEFAULT_OAUTH_REDIRECT_URI = "http://localhost:3000/oauth/redirect"
    }
}