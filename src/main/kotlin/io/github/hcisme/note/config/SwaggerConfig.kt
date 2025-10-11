package io.github.hcisme.note.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun swaggerOpenApi(): OpenAPI {
        return OpenAPI()
            .info(
                Info().title("note后台服务")
                    .description("api接口文档")
                    .version("v1.0.0")
            )
            .externalDocs(
                ExternalDocumentation()
                    .description("接口文档")
            )
            .components(
                Components().addSecuritySchemes(
                    "token",
                    SecurityScheme()
                        .type(SecurityScheme.Type.APIKEY)
                        .`in`(SecurityScheme.In.HEADER)
                        .name("token")
                        .description("用户 token")
                )
            )
            .addSecurityItem(SecurityRequirement().addList("token"))
    }
}
