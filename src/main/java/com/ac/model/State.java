package com.ac.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"name"})
public class State {

    private Integer id;

    private String name;
}
