package com.backend.boilerplate.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @version 0.0.1
 * @since 0.0.1
 */
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"uuid", "name"})
@Data
@NoArgsConstructor
@Schema(description = "The uuid and name of the role with user details.")
public class UserRoleDto {

    @Schema(description = "The uuid of the role")
    private UUID uuid;

    @Schema(description = "The name of the role")
    private String name;

}
