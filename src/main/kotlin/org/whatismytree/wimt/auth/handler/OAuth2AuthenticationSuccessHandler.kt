package org.whatismytree.wimt.auth.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.whatismytree.wimt.auth.dto.OAuthInfo
import org.whatismytree.wimt.user.UserService

@Component
class OAuth2AuthenticationSuccessHandler(
    private val userService: UserService
) : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?,
    ) {
        val token = OAuth2AuthenticationToken::class.java.cast(authentication)

        val oAuthInfo = OAuthInfo.fromToken(token)

        println(oAuthInfo) // FIXME : 완성 후 삭제
        val findUser = userService.findOrCreateUser(oAuthInfo)
        // TODO : 회원 닉네임 설정 여부에 따라 분기 처리
    }
}