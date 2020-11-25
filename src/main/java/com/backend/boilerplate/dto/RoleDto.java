package com.backend.boilerplate.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.1
 */
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"uuid", "name", "featureClaims"})
@Data
@EqualsAndHashCode(of = {"uuid"}, callSuper = false)
@NoArgsConstructor
@Schema(description = "All details about the Role.")
public class RoleDto extends BaseRoleDto {

    @Schema(description = "The name of the role")
    private String name;

    @Schema(description = "The uuid of the role")
    private UUID uuid;

}
