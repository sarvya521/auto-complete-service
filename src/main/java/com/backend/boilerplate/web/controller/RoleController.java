package com.backend.boilerplate.web.controller;

import com.sp.boilerplate.commons.constant.Status;
import com.backend.boilerplate.dto.CreateRoleDto;
import com.sp.boilerplate.commons.dto.Response;
import com.backend.boilerplate.dto.RoleDto;
import com.backend.boilerplate.dto.UpdateRoleDto;
import com.backend.boilerplate.dto.validator.ConstraintSequence;
import com.backend.boilerplate.dto.validator.Exist;
import com.backend.boilerplate.dto.validator.ExistRoleValidator;
import com.backend.boilerplate.service.RoleService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Bundles all CRUD APIs for Role.
 *
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@Tag(name = "Role CRUD API")
@RestController
@RequestMapping("/api/v1/role")
@Validated
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Operation(description = "Get a list of Roles with respective permissions")
    @GetMapping
    public Response<List<RoleDto>> getAllRoles() {
        List<RoleDto> roles = roleService.getAllRoles();
        return new Response<>(Status.SUCCESS, HttpStatus.OK.value(), roles);
    }

    @Operation(description = "Create an Role with respective permissions")
    @PostMapping
    public Response<RoleDto> createRole(
        @RequestBody @Validated(ConstraintSequence.class) CreateRoleDto createRoleDto) {
        RoleDto roleDto = roleService.createRole(createRoleDto);
        return new Response<>(Status.SUCCESS, HttpStatus.CREATED.value(), roleDto);
    }

    @Operation(description = "Update an Role with respective permissions")
    @PutMapping
    public Response<RoleDto> updateRole(
        @RequestBody @Validated(ConstraintSequence.class) UpdateRoleDto updateRoleDto) {
        RoleDto roleDto = roleService.updateRole(updateRoleDto);
        return new Response<>(Status.SUCCESS, HttpStatus.OK.value(), roleDto);
    }

    @SuppressWarnings("rawtypes")
    @Operation(description = "Delete an Role")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Success Response",
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
            description = "id of role",
            example = "97cfdf8e-8b8f-40e8-ba1f-3bffd2c05b97"
        )
    })
    @DeleteMapping("/{uuid}")
    public Response deleteRole(@PathVariable(value = "uuid")
                               @Exist(message = "1009", constraintValidator = ExistRoleValidator.class) UUID uuid) {
        roleService.deleteRole(uuid);
        return new Response<>(Status.SUCCESS, HttpStatus.OK.value());
    }

}
