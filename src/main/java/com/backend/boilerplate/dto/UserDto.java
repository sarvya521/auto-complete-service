package com.backend.boilerplate.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
 * @since 0.0.1
 */
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"uuid", "firstName", "middleName", "lastName", "email", "salutation", "createdAt", "roles"})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Schema(description = "All details about the user.")
public class UserDto extends BaseUserDto {

    @Schema(description = "The uuid of the user")
    private UUID uuid;

    /**
     * @since 0.0.2
     */
    @Schema(description = "The created time of the user")
    private long createdAt;

    @Schema(description = "The role details of the user")
    private List<UserRoleDto> roles;

}
