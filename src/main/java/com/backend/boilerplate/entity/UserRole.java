package com.backend.boilerplate.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.1
 */
@Entity
@Table(name = "user_role")
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
public class UserRole {
    @Getter
    @Setter
    @EqualsAndHashCode(of = {"userId", "roleId"})
    @NoArgsConstructor
    @Embeddable
    public static class UserRoleId implements Serializable {
        @Column(name = "fk_user_id")
        protected Long userId;

        @Column(name = "fk_role_id")
        protected Long roleId;

        public UserRoleId(Long userId, Long roleId) {
            this.userId = userId;
            this.roleId = roleId;
        }
    }

    @EmbeddedId
    private UserRoleId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id", insertable = false, updatable = false)
    private User user;

    @MapsId("roleId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_role_id", insertable = false, updatable = false)
    private Role role;

    @Column(name = "performed_by", nullable = false)
    private Long performedBy;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ts", columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private Date timestamp;

    public UserRole(User user, Role role, Long performedBy) {
        this.id = new UserRoleId(user.getId(), role.getId());
        this.user = user;
        this.role = role;
        this.performedBy = performedBy;
    }

    public void setUser(User user) {
        this.user = user;
        if (Objects.nonNull(user)) {
            this.id.setUserId(user.getId());
        }
    }

    public void setRole(Role role) {
        this.role = role;
        if (Objects.nonNull(role)) {
            this.id.setRoleId(role.getId());
        }
    }
}
