package com.backend.boilerplate.dto;

import com.backend.boilerplate.constant.Salutation;
import com.backend.boilerplate.dto.validator.ValidEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.2
 */
@Data
@EqualsAndHashCode(of = {"email"})
@NoArgsConstructor
@Schema(description = "Basic details of the user.")
public class BaseUserDto {

    @Schema(description = "The first name of the user")
    @NotBlank(message = "1072")
    @Size(min = 2, max = 50, message = "1102")
    @Pattern(regexp = "^[a-zA-Z]+[a-zA-Z'-]*[a-zA-Z]+", message = "1103")
    private String firstName;

    @Schema(description = "The middle name of the user")
    @Size(min = 0, max = 50, message = "1101")
    @Pattern(regexp = "^$|^[a-zA-Z]+[a-zA-Z'-]*[a-zA-Z]+", message = "1103")
    private String middleName;

    @Schema(description = "The last name of the user")
    @NotBlank(message = "1073")
    @Size(min = 2, max = 50, message = "1100")
    @Pattern(regexp = "^[a-zA-Z]+[a-zA-Z'-]*[a-zA-Z]+", message = "1103")
    private String lastName;

    @Schema(description = "The email id of the user", pattern = "^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\" +
        ".[a-zA-Z0-9-]+)*$")
    @NotBlank(message = "1071")
    @Size(min = 8, max = 256, message = "1099")
    @Email(message = "1048", regexp = "^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")
    private String email;

    @Schema(description = "The salutation of the user", allowableValues = {"MR", "MISS", "MS", "MRS", "DR"})
    @ValidEnum(enumClass = Salutation.class, message = "1078")
    private String salutation;
}