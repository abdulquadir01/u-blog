package com.aq.blogapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;




@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getInfo())
                .securityContexts(securityContexts())
                .securitySchemes(List.of(apiKeys()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiKey apiKeys(){
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    private List<SecurityContext> securityContexts(){
        return List.of(
                SecurityContext
                    .builder()
                    .securityReferences(securityReferences())
                    .build()
        );
    }

    private List<SecurityReference> securityReferences(){

        AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");
        return List.of(new SecurityReference("JWT", new AuthorizationScope[]{scope}));
    }

    private ApiInfo getInfo(){
        return new ApiInfo("BlogApp",
                "Backend of a simple app for blogging",
                "1.0",
                "TnC", new Contact("Abdul", "contactUrl", "abc@gmail.com"),
                "MIT licence 2",
                "licence URL", Collections.emptyList()
        );
    }



}
