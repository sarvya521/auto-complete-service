package com.sp.backend.boilerplate.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
@ToString(of = { "name" })
@ApiModel(description = "All details about the patient. ")
public class PatientDTO {
    @ApiModelProperty(notes = "The patient id")
    private Integer id;

    @ApiModelProperty(notes = "The patient first name")
    private String name;
}
 