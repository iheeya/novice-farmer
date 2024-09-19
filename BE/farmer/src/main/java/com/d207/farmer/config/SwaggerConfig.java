package com.d207.farmer.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
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

}
