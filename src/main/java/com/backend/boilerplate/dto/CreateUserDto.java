package com.backend.boilerplate.dto;

import com.backend.boilerplate.dto.validator.CreateUserValidator;
import com.backend.boilerplate.dto.validator.Extended;
import com.backend.boilerplate.dto.validator.ValidCreateResource;
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
@JsonPropertyOrder({"firstName", "middleName", "lastName", "email", "salutation", "roles"})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Schema(description = "All details to create the user.")
@ValidCreateResource(constraintValidator = CreateUserValidator.class, groups = Extended.class)
public class CreateUserDto extends BaseUserDto {

    @Schema(description = "The role details of the user")
    private List<UUID> roles;

}