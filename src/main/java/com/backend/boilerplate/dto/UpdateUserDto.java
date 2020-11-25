package com.backend.boilerplate.dto;

import com.backend.boilerplate.dto.validator.Extended;
import com.backend.boilerplate.dto.validator.UniqueResource;
import com.backend.boilerplate.dto.validator.UniqueUserValidator;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.2
 */
@JsonPropertyOrder({"uuid", "firstName", "middleName", "lastName", "email", "salutation", "roles"})
@Data
@EqualsAndHashCode(of = {"uuid"}, callSuper = false)
@NoArgsConstructor
@Schema(description = "All details to update the user.")
@UniqueResource(constraintValidator = UniqueUserValidator.class, groups = Extended.class)
public class UpdateUserDto extends BaseUserDto {

    @Schema(description = "The uuid of the user", required = true)
    private UUID uuid;

    @Schema(description = "The role details of the user")
    private List<UUID> roles;

}
