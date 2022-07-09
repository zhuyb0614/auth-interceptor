package com.github.zhuyb0614.auth;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

@Configuration
@EnableSwagger2
public class SwaggerConfig {


    private Parameter getTokenParameter() {
        return new ParameterBuilder()
                .parameterType("header")
                .name(HttpHeaders.AUTHORIZATION)
                .modelRef(new ModelRef("string"))
                .required(false)
                .build();
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(Arrays.asList(getTokenParameter()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .build();
    }
}