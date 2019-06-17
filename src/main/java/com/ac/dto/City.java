package com.ac.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = { "id" })
@ToString(of = { "name" })
public class City {

    private Integer id;

    private String name;

}
