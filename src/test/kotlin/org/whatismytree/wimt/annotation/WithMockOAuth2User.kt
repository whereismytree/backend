package org.whatismytree.wimt.annotation

import org.springframework.security.test.context.support.WithSecurityContext
import org.whatismytree.wimt.security.WithMockOAuth2UserSecurityContextFactory

@Retention(AnnotationRetention.RUNTIME)
@WithSecurityContext(factory = WithMockOAuth2UserSecurityContextFactory::class)
annotation class WithMockOAuth2User(
    val userId: Long = 1L,
    val registrationId: String = "google",
)
