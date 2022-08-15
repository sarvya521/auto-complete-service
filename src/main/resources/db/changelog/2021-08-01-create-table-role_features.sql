CREATE TABLE IF NOT EXISTS role_features (
    fk_role_id BINARY(16) NOT NULL,
    fk_module_features_id BINARY(16) NOT NULL,
    feature_action ENUM("VIEW", "CREATE", "UPDATE", "DELETE") NOT NULL,
    ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    performed_by VARCHAR(255) NOT NULL,
    PRIMARY KEY (fk_role_id, fk_module_features_id, feature_action),
    CONSTRAINT fk_role_features_role FOREIGN KEY (fk_role_id) REFERENCES role (id),
    CONSTRAINT fk_role_features_module_features FOREIGN KEY (fk_module_features_id) REFERENCES module_features (id)
);