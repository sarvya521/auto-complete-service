package com.backend.boilerplate.entity;

import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.1
 */
@Entity
@Table(name = "role_history")
@TypeDef(name = "Status", typeClass = PostgreSQLEnumType.class)
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RoleHistory {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", columnDefinition = "SERIAL")
    private Long id;

    @Column(name = "role_id", nullable = false)
    private Long roleId;

    @Column(name = "uuid", nullable = false)
    private UUID uuid;

    @Enumerated(EnumType.STRING)
    @Type(type = "Status")
    @Column(name = "status", columnDefinition = "Status", nullable = false)
    private Status status;

    @Column(name = "performed_by", nullable = false)
    private Long performedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ts", columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private Date timestamp;

    @Column(name = "name", nullable = false)
    private String name;

}
