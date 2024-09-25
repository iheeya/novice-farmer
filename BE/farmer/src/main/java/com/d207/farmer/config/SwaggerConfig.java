package com.d207.farmer.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "새싹농부 API",
                description = "새싹농부 API 입니다. 문의는 백엔드로",
                version = "v1.0"
        )
)
public class SwaggerConfig {
    // spring security 사용시 변경 필요

        @Bean
        public OpenAPI openAPI() {
             String jwtSchemaName = "jwtAuth";
                SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemaName);

                Components components = new Components()
                        .addSecuritySchemes(jwtSchemaName, new SecurityScheme()
                                .name(jwtSchemaName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer"));
//                                .bearerFormat("JWT"));

                return new OpenAPI()
                        .components(new Components())
                        .addSecurityItem(securityRequirement)
                        .components(components);
        }
}
