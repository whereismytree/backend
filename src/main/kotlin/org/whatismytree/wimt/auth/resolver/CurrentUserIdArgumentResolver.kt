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
import org.whatismytree.wimt.user.exception.UserNotFoundException
import org.whatismytree.wimt.user.repository.UserRepository

class CurrentUserIdArgumentResolver(
    private val userRepository: UserRepository,
) : HandlerMethodArgumentResolver {
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
        val userId = getCurrentUserId()

        validateUserExists(userId)

        return userId
    }

    private fun getCurrentUserId(): Long {
        try {
            val oAuth2User = SecurityContextHolder.getContext().authentication.principal as OAuth2User
            return oAuth2User.name.toLong()
        } catch (e: ClassCastException) {
            throw LoginRequiredException("유효하지 않은 토큰입니다. 다시 로그인해주세요.", e)
        }
    }

    private fun validateUserExists(userId: Long) {
        if (!userRepository.existsByIdAndDeletedAtIsNull(userId)) {
            throw UserNotFoundException("존재하지 않거나 탈퇴한 유저입니다. userId: $userId")
        }
    }
}
