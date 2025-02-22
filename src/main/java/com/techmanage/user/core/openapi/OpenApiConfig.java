package com.techmanage.user.core.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "TechManage API",
                version = "1.0",
                description = "Aplicação para gestão de usuários."
        )
)
public class OpenApiConfig {
}
