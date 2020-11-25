package com.backend.boilerplate.entity;

import com.backend.boilerplate.constant.FeatureAction;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.2
 */
@Entity
@Table(name = "module_features_claim")
@TypeDef(name = "FeatureAction", typeClass = PostgreSQLEnumType.class)
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
public class ModuleFeaturesClaim {
    @Getter
    @Setter
    @EqualsAndHashCode(of = {"moduleFeaturesId", "claimId", "featureAction"})
    @NoArgsConstructor
    @Embeddable
    public static class ModuleFeaturesClaimId implements Serializable {
        @Column(name = "fk_module_features_id")
        protected Long moduleFeaturesId;

        @Column(name = "fk_claim_id")
        protected Long claimId;

        @Enumerated(EnumType.STRING)
        @Type(type = "FeatureAction")
        @Column(name = "feature_action", columnDefinition = "FeatureAction")
        private FeatureAction featureAction;

        public ModuleFeaturesClaimId(Long moduleFeaturesId, Long claimId, FeatureAction featureAction) {
            this.moduleFeaturesId = moduleFeaturesId;
            this.claimId = claimId;
            this.featureAction = featureAction;
        }
    }

    @EmbeddedId
    private ModuleFeaturesClaimId id;

    @ManyToOne
    @JoinColumn(name = "fk_module_features_id", insertable = false, updatable = false)
    private ModuleFeatures moduleFeatures;

    @ManyToOne
    @JoinColumn(name = "fk_claim_id", insertable = false, updatable = false)
    private Claim claim;

    @Enumerated(EnumType.STRING)
    @Type(type = "FeatureAction")
    @Column(name = "feature_action", columnDefinition = "FeatureAction", insertable = false, updatable = false)
    private FeatureAction featureAction;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ts", columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private Date timestamp;

    public ModuleFeaturesClaim(ModuleFeatures moduleFeatures, Claim claim, FeatureAction featureAction) {
        this.id = new ModuleFeaturesClaimId(moduleFeatures.getId(), claim.getId(), featureAction);
        this.moduleFeatures = moduleFeatures;
        this.claim = claim;
        this.featureAction = featureAction;
    }

    public void setModuleFeatures(ModuleFeatures moduleFeatures) {
        this.moduleFeatures = moduleFeatures;
        this.id.setModuleFeaturesId(moduleFeatures.getId());
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
        this.id.setClaimId(claim.getId());
    }

    public void setFeatureAction(FeatureAction featureAction) {
        this.featureAction = featureAction;
        this.id.setFeatureAction(featureAction);
    }
}
