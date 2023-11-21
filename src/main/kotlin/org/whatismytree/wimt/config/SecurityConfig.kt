package org.whatismytree.wimt.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.whatismytree.wimt.auth.handler.OAuth2AuthenticationSuccessHandler

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val oAuth2AuthenticationSuccessHandler: OAuth2AuthenticationSuccessHandler
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { csrf -> csrf.disable() }
            .sessionManagement { session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .formLogin { formLogin -> formLogin.disable() }
            .httpBasic { httpBasic -> httpBasic.disable() }
            .oauth2Login { oauth2 -> oauth2
                .successHandler(oAuth2AuthenticationSuccessHandler)
            }
            .build()
    }
}