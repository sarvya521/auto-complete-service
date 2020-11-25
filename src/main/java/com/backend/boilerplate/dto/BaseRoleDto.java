package com.backend.boilerplate.dto;

import com.backend.boilerplate.constant.FeatureAction;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.2
 */
@Data
@NoArgsConstructor
@Schema(description = "Basic details about the role.")
public class BaseRoleDto {
    @Schema(description = "FeatureClaims for this role")
    @NotEmpty(message = "1074")
    private Map<String, List<FeatureAction>> featureClaims;
}
