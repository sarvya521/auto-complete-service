package com.backend.boilerplate.entity;

import lombok.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.2
 */
@Entity
@Table(name = "module_features",
    uniqueConstraints =
    @UniqueConstraint(columnNames = {"fk_module_id", "name"}))
@TypeDef(name = "Status", typeClass = PostgreSQLEnumType.class)
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@AllArgsConstructor
@NoArgsConstructor
public class ModuleFeatures {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", columnDefinition = "SERIAL")
    private Long id;

    @Generated(GenerationTime.INSERT)
    @Column(name = "uuid", columnDefinition = "UUID DEFAULT gen_random_uuid()", nullable = false, insertable = false,
        updatable = false, unique = true)
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    private UUID uuid;

    @Enumerated(EnumType.STRING)
    @Type(type = "Status")
    @Column(name = "status", columnDefinition = "Status", nullable = false)
    private Status status;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ts", columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private Date timestamp;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_module_id", nullable = false)
    private Module module;

    @OneToMany(mappedBy = "moduleFeatures", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ModuleFeaturesClaim> moduleFeaturesClaims = new ArrayList<>();

    @OneToMany(mappedBy = "moduleFeatures", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<RoleFeatures> roleFeatures = new ArrayList<>();
}
