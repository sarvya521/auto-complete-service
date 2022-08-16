-- liquibase formatted sql
-- changeset Sarvesh:create-table-module_features

CREATE TABLE IF NOT EXISTS module_features (
    id BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID(), TRUE)) PRIMARY KEY,
    fk_module_id BINARY(16) NOT NULL,
    name VARCHAR(512) NOT NULL,
    description VARCHAR(1000),
    ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM("CREATED","UPDATED","DEACTIVATED","REACTIVATED") NOT NULL,
    CONSTRAINT uk_module_features_name UNIQUE (name),
    CONSTRAINT uk_module_features_module_name UNIQUE (fk_module_id, name),
    CONSTRAINT fk_module_features_module FOREIGN KEY (fk_module_id) REFERENCES module (id)
);