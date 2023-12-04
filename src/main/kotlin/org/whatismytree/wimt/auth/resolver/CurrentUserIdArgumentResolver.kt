package org.whatismytree.wimt.auth.resolver

import org.springframework.core.MethodParameter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import org.whatismytree.wimt.auth.exception.LoginRequiredException
import org.whatismytree.wimt.common.CurrentUserId

class CurrentUserIdArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.parameterType == Long::class.java &&
            parameter.hasParameterAnnotation(CurrentUserId::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): Any {
        val oAuth2User = SecurityContextHolder.getContext().authentication?.principal as OAuth2User?
//            ?: throw AccessDeniedException("로그인이 필요합니다.")
//            ?: throw IllegalStateException("로그인이 필요합니다.")
            ?: throw LoginRequiredException("로그인이 필요합니다.")
        return oAuth2User.name.toLong()
    }
}
