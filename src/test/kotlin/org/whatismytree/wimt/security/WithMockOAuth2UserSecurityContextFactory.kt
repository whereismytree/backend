package org.whatismytree.wimt.security

import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.test.context.support.WithSecurityContextFactory
import org.whatismytree.wimt.annotation.WithMockOAuth2User

class WithMockOAuth2UserSecurityContextFactory : WithSecurityContextFactory<WithMockOAuth2User> {
    override fun createSecurityContext(oauth2User: WithMockOAuth2User): SecurityContext {
        val context = SecurityContextHolder.createEmptyContext()

        val attributes = mapOf(
            USER_ID to oauth2User.userId,
            OAUTH_TYPE to oauth2User.registrationId,
        )

        val principal = DefaultOAuth2User(
            listOf(),
            attributes,
            USER_ID,
        )

        val token = OAuth2AuthenticationToken(
            principal,
            principal.authorities,
            oauth2User.registrationId,
        )

        context.authentication = token
        return context
    }

    companion object {
        private const val USER_ID = "userId"
        private const val OAUTH_TYPE = "registrationId"
    }
}
