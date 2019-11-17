/*
 * Copyright (c) 2019 www.roche.com.
 * All rights reserved.
 */

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
@Table(name = "role_claim")
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
public class RoleClaim implements Serializable {
    @Getter
    @Setter
    @EqualsAndHashCode(of = {"roleId", "claimId"})
    @NoArgsConstructor
    @Embeddable
    public static class RoleClaimId implements Serializable {
        @Column(name = "fk_role_id")
        protected Long roleId;

        @Column(name = "fk_claim_id")
        protected Long claimId;

        public RoleClaimId(Long roleId, Long claimId) {
            this.roleId = roleId;
            this.claimId = claimId;
        }
    }

    @EmbeddedId
    private RoleClaimId id;

    @ManyToOne
    @JoinColumn(name = "fk_role_id", insertable = false, updatable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "fk_claim_id", insertable = false, updatable = false)
    private Claim claim;

    public RoleClaim(Role role, Claim claim) {
        this.id = new RoleClaimId(role.getId(), claim.getId());
        this.role = role;
        this.claim = claim;
    }

    public void setRole(Role role) {
        this.role = role;
        this.id.setRoleId(role.getId());
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
        this.id.setClaimId(claim.getId());
    }
}
