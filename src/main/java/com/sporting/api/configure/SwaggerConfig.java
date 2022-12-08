package com.sporting.api.configure;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private static final String REGEX_API = "/api/v.*";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(postPaths())
                .build();
    }

    /**
     * Filter api
     *
     * @return
     */
    private Predicate<String> postPaths() {
        return or(regex(REGEX_API));
    }

    /**
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Spring Boot REST API",
                "Spring Boot REST API for Sporting Shop",
                "1.0",
                "Terms of service",
                new Contact("Ngo Tuan Phuc", "www.example.com", "modani130484@gmail.com"),
                "MIT-License", "https://en.wikipedia.org/wiki/MIT_License", Collections.emptyList());
    }

    //end
}
