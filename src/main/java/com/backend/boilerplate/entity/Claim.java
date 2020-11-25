package com.backend.boilerplate.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.UUID;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.1
 */
@Entity
@Table(name = "claim")
@TypeDef(name = "Status", typeClass = PostgreSQLEnumType.class)
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@AllArgsConstructor
@NoArgsConstructor
public class Claim {
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
    private String resourceName;

    @Column(name = "api_http_method", nullable = false)
    private String resourceHttpMethod;

    @Column(name = "api_endpoint", nullable = false)
    private String resourceEndpoint;

    @Column(name = "is_admin", columnDefinition = "boolean default false")
    private boolean admin;

    public Claim(String resourceName, String resourceHttpMethod, String resourceEndpoint, Status status) {
        this.resourceName = resourceName;
        this.resourceHttpMethod = resourceHttpMethod;
        this.resourceEndpoint = resourceEndpoint;
        this.status = status;
    }
}
