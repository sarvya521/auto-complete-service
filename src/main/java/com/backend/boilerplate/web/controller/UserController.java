package com.backend.boilerplate.web.controller;

import com.backend.boilerplate.dto.CreateUserDto;
import com.backend.boilerplate.dto.UpdateUserDto;
import com.backend.boilerplate.dto.UserDto;
import com.backend.boilerplate.dto.UserPageDto;
import com.backend.boilerplate.dto.validator.ConstraintSequence;
import com.backend.boilerplate.dto.validator.Exist;
import com.backend.boilerplate.dto.validator.ExistUserValidator;
import com.backend.boilerplate.entity.User;
import com.backend.boilerplate.service.PaginationService;
import com.backend.boilerplate.service.UserService;
import com.sp.boilerplate.commons.constant.Status;
import com.sp.boilerplate.commons.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Bundles all CRUD APIs for User.
 *
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@Tag(name = "User CRUD API")
@RestController
@RequestMapping("/api/v1/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("UserPaginationService")
    private PaginationService<User, UserPageDto> userPaginationService;

    /**
     * @return Response<UserPageDto>
     * @since 0.0.1
     */
    @Operation(description = "Get a current page of list of Users")
    @ApiResponse(
        responseCode = "200",
        description = "API will return existing users in pagination",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = Response.class),
            examples = @ExampleObject(value =
                "{\"status\": \"SUCCESS\", \"code\": 200, \"data\": {\"id\": " +
                    "\"97cfdf8e-8b8f-40e8-ba1f-3bffd2c05b97\", \"identifier\": " +
                    "\"4f0df1d1-92d0-40f8-8694-c235a3649e49\", " +
                    "\"name\": \"Hipolito\" }, \"errors\": null }"
            )
        )
    )
    @GetMapping
    public Response<UserPageDto> getUsers(
        @RequestParam(required = false, value = "pageNo") Integer pageNo,
        @RequestParam(required = false, value = "pageSize") Integer pageSize,
        @RequestParam(required = false, value = "sortBy") String sortBy,
        @RequestParam(required = false, defaultValue = "true", value = "asc") boolean asc) {
        UserPageDto userPage = userPaginationService.getPageDto(pageNo, pageSize, sortBy, asc);
        return new Response<>(Status.SUCCESS, HttpStatus.OK.value(), userPage);
    }

    @Operation(description = "Get an User by UUID")
    @ApiResponse(
        responseCode = "200",
        description = "API will return user for given uuid",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = Response.class),
            examples = @ExampleObject(value =
                "{\"status\": \"SUCCESS\", \"code\": 200, \"data\": {\"id\": " +
                    "\"97cfdf8e-8b8f-40e8-ba1f-3bffd2c05b97\", \"identifier\": " +
                    "\"4f0df1d1-92d0-40f8-8694-c235a3649e49\", " +
                    "\"name\": \"Hipolito\" }, \"errors\": null }"
            )
        )
    )
    @Parameters({
        @Parameter(
            name = "uuid",
            schema = @Schema(type = "string"),
            in = ParameterIn.PATH,
            description = "id of user",
            example = "97cfdf8e-8b8f-40e8-ba1f-3bffd2c05b97"
        )
    })
    @GetMapping("/{uuid}")
    public Response<UserDto> getUserByUuid(@PathVariable(value = "uuid") UUID uuid) {
        UserDto user = userService.getUserByUuid(uuid);
        return new Response<>(Status.SUCCESS, HttpStatus.OK.value(), user);
    }

    @Operation(description = "Create an User")
    @ApiResponse(
        responseCode = "201",
        description = "API will return newly created user",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = Response.class),
            examples = @ExampleObject(value =
                "{\"status\": \"SUCCESS\", \"code\": 200, \"data\": {\"id\": " +
                    "\"97cfdf8e-8b8f-40e8-ba1f-3bffd2c05b97\", \"identifier\": " +
                    "\"4f0df1d1-92d0-40f8-8694-c235a3649e49\", " +
                    "\"name\": \"Hipolito\" }, \"errors\": null }"
            )
        )
    )
    @PostMapping
    public Response<UserDto> createUser(
        @RequestBody @Validated(ConstraintSequence.class) CreateUserDto createUserDto) {
        UserDto userDto = userService.createUser(createUserDto);
        return new Response<>(Status.SUCCESS, HttpStatus.CREATED.value(), userDto);
    }

    @Operation(description = "Update an User")
    @ApiResponse(
        responseCode = "200",
        description = "API will return updated user",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = Response.class),
            examples = @ExampleObject(value =
                "{\"status\": \"SUCCESS\", \"code\": 200, \"data\": {\"id\": " +
                    "\"97cfdf8e-8b8f-40e8-ba1f-3bffd2c05b97\", \"identifier\": " +
                    "\"4f0df1d1-92d0-40f8-8694-c235a3649e49\", " +
                    "\"name\": \"Hipolito\" }, \"errors\": null }"
            )
        )
    )
    @PutMapping
    public Response<UserDto> updateUser(
        @RequestBody @Validated(ConstraintSequence.class) UpdateUserDto updateUserDto) {
        UserDto userDto = userService.updateUser(updateUserDto);
        return new Response<>(Status.SUCCESS, HttpStatus.OK.value(), userDto);
    }

    @SuppressWarnings("rawtypes")
    @Operation(description = "Delete an User")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Success Response ",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = Response.class),
                examples = @ExampleObject(value =
                    "{\"status\":\"SUCCESS\",\"code\":200,\"errors\":[]}"
                )
            )
        )
    })
    @Parameters({
        @Parameter(
            name = "id",
            schema = @Schema(type = "string"),
            in = ParameterIn.PATH,
            description = "id of user",
            example = "97cfdf8e-8b8f-40e8-ba1f-3bffd2c05b97"
        )
    })
    @DeleteMapping("/{uuid}")
    public Response deleteUser(@PathVariable(value = "uuid")
                               @Exist(message = "1004", constraintValidator = ExistUserValidator.class) UUID uuid) {
        userService.deleteUser(uuid);
        return new Response<>(Status.SUCCESS, HttpStatus.OK.value());
    }
}