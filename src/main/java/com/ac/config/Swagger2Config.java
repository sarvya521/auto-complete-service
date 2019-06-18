package com.ac.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    private static final String BASE_PACKAGE = "com.ac.web.controller";
    private static final String GROUP_NAME = "auto-complete-api-%s";

    /**
     * @return Docket
     */
    @Bean
    public Docket swaggerApi21() {
        final String version = "2.1";
        return new Docket(DocumentationType.SWAGGER_2).groupName(String.format(GROUP_NAME, version))
            .select()
            .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
            .paths(regex("/api/v"+version+".*"))
            .build()
            .apiInfo(apiEndPointsInfo(version));
    }

    /**
     * @return Docket
     */
    @Bean
    public Docket swaggerApi20() {
        final String version = "2.0";
        return new Docket(DocumentationType.SWAGGER_2).groupName(String.format(GROUP_NAME, version))
            .select()
            .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
            .paths(regex("/api/v"+version+".*"))
            .build()
            .apiInfo(apiEndPointsInfo(version));
    }

    /**
     * @deprecated
     * @return Docket
     */
    @Deprecated(since = "2.0", forRemoval = true)
    @Bean
    public Docket swaggerApi10() {
        final String version = "1.0";
        return new Docket(DocumentationType.SWAGGER_2).groupName(String.format(GROUP_NAME, version))
            .select()
            .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
            .paths(regex("/api/v"+version+".*"))
            .build()
            .apiInfo(apiEndPointsInfo(version));
    }

    private ApiInfo apiEndPointsInfo(String version) {
        return new ApiInfoBuilder().title("AutoComplete API")
            .description("Documentation AutoComplete API v" + version)
            .contact(new Contact("Sarvesh Padwal", "www.google.com", "sarveshp@unifytech.com"))
            .license("Apache 2.0")
            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
            .version(version)
            .build();
    }
}
