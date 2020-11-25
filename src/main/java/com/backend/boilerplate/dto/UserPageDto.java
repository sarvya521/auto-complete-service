package com.backend.boilerplate.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.2
 */
@JsonInclude(content = JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"totalRecords", "users"})
@Data
@NoArgsConstructor
@Schema(description = "Paginated result of user list")
public class UserPageDto {

    @Schema(description = "The total number available users")
    private Long totalRecords = 0l;

    @Schema(description = "List of user with user details")
    private List<UserDto> users = new ArrayList<>();

}
