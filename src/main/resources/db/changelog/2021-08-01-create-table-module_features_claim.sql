-- liquibase formatted sql
-- changeset Sarvesh:create-table-module_feature_claims

CREATE TABLE IF NOT EXISTS module_features_claim (
    fk_module_features_id BINARY(16) NOT NULL,
    feature_action ENUM("VIEW", "CREATE", "UPDATE", "DELETE") NOT NULL,
    fk_claim_id BINARY(16) NOT NULL,
    ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (fk_module_features_id, feature_action, fk_claim_id),
    CONSTRAINT fk_module_features_claim_module_features FOREIGN KEY (fk_module_features_id) REFERENCES module_features (id),
    CONSTRAINT fk_module_features_claim_claim FOREIGN KEY (fk_claim_id) REFERENCES claim (id)
);
