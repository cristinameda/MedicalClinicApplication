package com.meda.titu.medicalclinicapplication.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI api() {
        final String securitySchemeName = "Bearer Authentication";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName).addList(""))
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")))
                .info(
                        new Info()
                                .title("Medical Clinic API")
                                .description("UTCN Thesis")
                                .version("V0.0.1")
                                .license(new License().name("License name: Medical Clinic")))
                .externalDocs(new ExternalDocumentation().description("Medical Clinic API Documentation"));
    }

    @Bean
    public GroupedOpenApi groupPublicApis() {
        return GroupedOpenApi.builder()
                .group("public-apis")
                .pathsToMatch("/auth/login")
                .build();
    }

    @Bean
    public GroupedOpenApi groupPrivateApis() {
        return GroupedOpenApi.builder()
                .group("private-apis")
                .pathsToMatch("/**")
                .pathsToExclude("/auth/login")
                .build();
    }
}
