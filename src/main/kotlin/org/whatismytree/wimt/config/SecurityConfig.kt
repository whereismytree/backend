package org.whatismytree.wimt.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { csrf -> csrf.disable() }
            .sessionManagement { session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .formLogin { formLogin -> formLogin.disable() }
            .httpBasic { httpBasic -> httpBasic.disable() }
            .oauth2Login { oauth2 -> oauth2
                .successHandler { _, _, _ -> println("로그인 성공") }    // TODO: 추후 성공 시 로직 추가 (회원 닉네임 설정 여부)
            }
            .build()
    }
}