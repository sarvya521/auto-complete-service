package com.backend.boilerplate.entity;

import com.sp.boilerplate.commons.constant.FeatureAction;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.2
 */
@Entity
@Table(name = "role_features")
@TypeDef(name = "FeatureAction", typeClass = PostgreSQLEnumType.class)
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@AllArgsConstructor
@NoArgsConstructor
public class RoleFeatures {
    @Getter
    @Setter
    @EqualsAndHashCode(of = {"roleId", "moduleFeaturesId", "featureAction"})
    @NoArgsConstructor
    @Embeddable
    public static class RoleFeaturesId implements Serializable {
        @Column(name = "fk_role_id")
        protected Long roleId;

        @Column(name = "fk_module_features_id")
        protected Long moduleFeaturesId;

        @Enumerated(EnumType.STRING)
        @Type(type = "FeatureAction")
        @Column(name = "feature_action", columnDefinition = "FeatureAction")
        private FeatureAction featureAction;

        public RoleFeaturesId(Long roleId, Long moduleFeaturesId, FeatureAction featureAction) {
            this.roleId = roleId;
            this.moduleFeaturesId = moduleFeaturesId;
            this.featureAction = featureAction;
        }
    }

    @EmbeddedId
    private RoleFeaturesId id;

    @MapsId("roleId")
    @ManyToOne
    @JoinColumn(name = "fk_role_id", insertable = false, updatable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "fk_module_features_id", insertable = false, updatable = false)
    private ModuleFeatures moduleFeatures;

    @Enumerated(EnumType.STRING)
    @Type(type = "FeatureAction")
    @Column(name = "feature_action", columnDefinition = "FeatureAction", insertable = false, updatable = false)
    private FeatureAction featureAction;

    @Column(name = "performed_by", nullable = false)
    private Long performedBy;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ts", columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private Date timestamp;

    public RoleFeatures(Role role, ModuleFeatures moduleFeatures, FeatureAction featureAction, Long performedBy) {
        this.id = new RoleFeaturesId(role.getId(), moduleFeatures.getId(), featureAction);
        this.role = role;
        this.moduleFeatures = moduleFeatures;
        this.featureAction = featureAction;
        this.performedBy = performedBy;
    }

    public void setRole(Role role) {
        this.role = role;
        this.id.setRoleId(role.getId());
    }

    public void setModuleFeatures(ModuleFeatures moduleFeatures) {
        this.moduleFeatures = moduleFeatures;
        this.id.setModuleFeaturesId(moduleFeatures.getId());
    }

    public void setFeatureAction(FeatureAction featureAction) {
        this.featureAction = featureAction;
        this.id.setFeatureAction(featureAction);
    }
}
