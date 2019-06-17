package com.ac.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "mst_city")
@Data
@EqualsAndHashCode(of = { "id" })
@ToString(of = { "name" })
public class MstCity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    private String name;
}
