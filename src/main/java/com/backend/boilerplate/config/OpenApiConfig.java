package com.backend.boilerplate.config;

import com.backend.boilerplate.constant.Status;
import com.backend.boilerplate.dto.Response;
import com.backend.boilerplate.exception.ErrorDetails;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;
import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.2
 */
@Log4j2
@Profile({"!test", "!int"})
@Configuration
public class OpenApiConfig {

    @Autowired
    private BuildProperties buildProperties;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(
                new Info()
                    .title("USERMANAGEMENT API")
                    .version(buildProperties.getVersion())
                    .license(
                        new License()
                            .name("Apache 2.0")
                            .url("http://www.apache.org/licenses/LICENSE-2.0.html")
                    )
            );
    }

    @Bean
    public OperationCustomizer customize() {
        return (operation, handlerMethod) -> {
            final List<Parameter> parameters = operation.getParameters();
            if (Objects.nonNull(parameters)) {
                boolean commonHeadersAddedAlready = parameters.stream()
                    .anyMatch(parameter ->
                        Objects.equals(parameter.getName(), "Authorization")
                    );
                if (commonHeadersAddedAlready) {
                    return operation;
                }
            }
            return
                operation
                    .addParametersItem(
                        new Parameter()
                            .in(ParameterIn.HEADER.toString())
                            .required(true)
                            .name("Authorization")
                    )
                    .responses(
                        new ApiResponses()
                            .addApiResponse("400", badRequestApiResponse())
                            .addApiResponse("500", serverErrorApiResponse())
                            .addApiResponse("401", authenticationFailApiResponse())
                            .addApiResponse("403", forbiddenAccessApiResponse())
                    );
        };
    }

    private ApiResponse badRequestApiResponse() {
        Response response = new Response();
        response.setStatus(Status.CLIENT_ERROR);
        response.setCode(400);
        response.setErrors(List.of(new ErrorDetails("_error_code", "_error_message")));
        return
            new ApiResponse()
                .description("ClientError")
                .content(
                    new Content()
                        .addMediaType(APPLICATION_JSON_VALUE,
                            new MediaType().schema(new Schema().example(response)))
                );
    }

    private ApiResponse serverErrorApiResponse() {
        Response response = new Response();
        response.setStatus(Status.FAIL);
        response.setCode(500);
        response.setErrors(List.of(new ErrorDetails("_error_code", "_error_message")));
        return
            new ApiResponse()
                .description("ServerError")
                .content(
                    new Content()
                        .addMediaType(APPLICATION_JSON_VALUE,
                            new MediaType().schema(new Schema().example(response)))
                );
    }

    private ApiResponse authenticationFailApiResponse() {
        Response response = new Response();
        response.setStatus(Status.CLIENT_ERROR);
        response.setCode(401);
        response.setErrors(List.of(new ErrorDetails("401", "AuthenticationFail")));
        return
            new ApiResponse()
                .description("AuthenticationFail")
                .content(
                    new Content()
                        .addMediaType(APPLICATION_JSON_VALUE,
                            new MediaType().schema(new Schema().example(response)))
                );
    }

    private ApiResponse forbiddenAccessApiResponse() {
        Response response = new Response();
        response.setStatus(Status.CLIENT_ERROR);
        response.setCode(403);
        response.setErrors(List.of(new ErrorDetails("403", "ForbiddenAccess")));
        return
            new ApiResponse()
                .description("ForbiddenAccess")
                .content(
                    new Content()
                        .addMediaType(APPLICATION_JSON_VALUE,
                            new MediaType().schema(new Schema().example(response)))
                );
    }
}
