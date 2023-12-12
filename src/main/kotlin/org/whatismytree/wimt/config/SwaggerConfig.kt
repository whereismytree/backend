package org.whatismytree.wimt.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springdoc.core.customizers.OperationCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.http.HttpHeaders
import org.whatismytree.wimt.common.CurrentUserId

@Configuration
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI = OpenAPI()
        .components(
            Components().addSecuritySchemes(SECURITY_SCHEME_KEY, jwtSecurityScheme()),
        )
        .info(apiInfo())
        .servers(listOf(Server().url("https://devjyp.shop"), Server().url("http://localhost:8080")))

    private fun jwtSecurityScheme(): SecurityScheme = SecurityScheme()
        .name(HttpHeaders.AUTHORIZATION)
        .`in`(SecurityScheme.In.HEADER)
        .type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT")
        .description("JWT 인증을 위해 Bearer 접두사 없이 토큰을 입력하세요.")

    private fun apiInfo() = Info()
        .title("어쩔트리 API")
        .description("어쩔트리 API Documentation")
        .version("1.0.0")

    @Bean
    fun customOperationCustomizer(): OperationCustomizer {
        return OperationCustomizer { operation, handlerMethod ->
            handlerMethod.methodParameters
                .firstOrNull {
                    it.parameterType == Long::class.java &&
                        it.hasParameterAnnotation(CurrentUserId::class.java)
                }
                ?.let { paramToRemove ->
                    operation.addSecurityItem(SecurityRequirement().addList(SECURITY_SCHEME_KEY))
                    operation.parameters.removeIf { param -> param.name == getParamName(paramToRemove) }
                }
            operation
        }
    }

    private fun getParamName(methodParameter: MethodParameter): String =
        methodParameter.parameterName
            ?: methodParameter.parameter.declaringExecutable.parameters[methodParameter.parameterIndex].name

    companion object {
        private const val SECURITY_SCHEME_KEY = "bearer-jwt"
    }
}
