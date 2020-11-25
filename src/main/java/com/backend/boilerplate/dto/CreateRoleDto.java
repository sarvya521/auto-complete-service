package com.backend.boilerplate.dto;

import com.backend.boilerplate.dto.validator.CreateRoleValidator;
import com.backend.boilerplate.dto.validator.Extended;
import com.backend.boilerplate.dto.validator.ValidCreateResource;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.2
 */
@JsonPropertyOrder({"name", "featureClaims"})
@Data
@EqualsAndHashCode(of = {"name"}, callSuper = false)
@NoArgsConstructor
@Schema(description = "All details about creating the role.")
@ValidCreateResource(constraintValidator = CreateRoleValidator.class, groups = Extended.class)
public class CreateRoleDto extends BaseRoleDto {
    @Schema(description = "The name of the role", maxLength = 50)
    @NotBlank(message = "1010")
    @Size(max = 50, message = "1011")
    private String name;
}
