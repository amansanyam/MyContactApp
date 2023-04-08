package com.contactapi.mycontactapp.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;


@OpenAPIDefinition
@Configuration
@SecurityScheme(
        name = "token",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SpringDocConfig implements WebMvcConfigurer{
    
    

    @Bean
    public OpenAPI baseOpenAPI(){
        return new OpenAPI().info(new Info().title("Contact Management System").version("1.0.0").description("My Contact App"));
    }

    @Bean
    public GroupedOpenApi authenticationApi(){
        String [] paths = {"/api/contacts/auth/**"};
        return GroupedOpenApi.builder()
                .group("Authentication")
                .pathsToMatch(paths)
                .build();
    }
    
    @Bean
    public GroupedOpenApi postApi(){
        String [] paths = {"/api/contacts/**"};
        return GroupedOpenApi.builder()
                .group("Post")
                .pathsToMatch(paths)
                .build();
    }
}
