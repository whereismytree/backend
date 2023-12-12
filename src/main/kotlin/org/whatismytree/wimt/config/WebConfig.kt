package org.whatismytree.wimt.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.whatismytree.wimt.auth.resolver.CurrentUserIdArgumentResolver
import org.whatismytree.wimt.user.repository.UserRepository

@Configuration
class WebConfig(
    private val userRepository: UserRepository,
) : WebMvcConfigurer {
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(CurrentUserIdArgumentResolver(userRepository))
    }
}
