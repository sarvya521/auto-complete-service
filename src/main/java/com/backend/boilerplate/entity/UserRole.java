package com.backend.boilerplate.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity
@Table(name = "user_role")
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
public class UserRole implements Serializable {
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

    @ManyToOne
    @JoinColumn(name = "fk_user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "fk_role_id", insertable = false, updatable = false)
    private Role role;

    public UserRole(User user, Role role) {
        this.id = new UserRoleId(user.getId(), role.getId());
        this.user = user;
        this.role = role;
    }

    public void setUser(User user) {
        this.user = user;
        this.id.setUserId(user.getId());
    }

    public void setRole(Role role) {
        this.role = role;
        this.id.setRoleId(role.getId());
    }
}
