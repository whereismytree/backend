package org.whatismytree.wimt.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springdoc.core.customizers.OperationCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.whatismytree.wimt.common.CurrentUserId

@Configuration
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI = OpenAPI()
        .components(Components())
        .info(apiInfo())

    @Bean
    fun customOperationCustomizer(): OperationCustomizer {
        return OperationCustomizer { operation, handlerMethod ->
            handlerMethod.methodParameters
                .firstOrNull {
                    it.parameterType == Long::class.java &&
                        it.hasParameterAnnotation(CurrentUserId::class.java)
                }
                ?.let { paramToRemove ->
                    operation.parameters.removeIf { param -> param.name == getParamName(paramToRemove) }
                }
            operation
        }
    }

    private fun getParamName(methodParameter: MethodParameter): String =
        methodParameter.parameterName
            ?: methodParameter.parameter.declaringExecutable.parameters[methodParameter.parameterIndex].name

    private fun apiInfo() = Info()
        .title("어쩔트리 API")
        .description("어쩔트리 API Documentation")
        .version("1.0.0")
}
