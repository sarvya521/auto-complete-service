package com.backend.boilerplate.dto;

import com.backend.boilerplate.dto.validator.Extended;
import com.backend.boilerplate.dto.validator.UniqueResource;
import com.backend.boilerplate.dto.validator.UniqueRoleValidator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;


/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.2
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"uuid", "name", "featureClaims"})
@Data
@EqualsAndHashCode(of = {"uuid"}, callSuper = false)
@NoArgsConstructor
@Schema(description = "All details about updating the role.")
@UniqueResource(constraintValidator = UniqueRoleValidator.class, groups = Extended.class)
public class UpdateRoleDto extends BaseRoleDto {
    @Schema(description = "The uuid of the role")
    @NotNull(message = "1013")
    private UUID uuid;
}
