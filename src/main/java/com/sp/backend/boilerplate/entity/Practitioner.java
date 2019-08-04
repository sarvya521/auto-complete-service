package com.sp.backend.boilerplate.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "practitioner")
@Data
@EqualsAndHashCode(of = { "id" })
@ToString(of = { "name" })
public class Practitioner {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    private String name;
}
