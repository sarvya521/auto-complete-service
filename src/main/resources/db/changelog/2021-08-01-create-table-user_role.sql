-- liquibase formatted sql
-- changeset Sarvesh:create-table-user_role

CREATE TABLE IF NOT EXISTS user_role (
  fk_user_id BINARY(16) NOT NULL,
  fk_role_id BINARY(16) NOT NULL,
  performed_by VARCHAR(255) NOT NULL,
  ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (fk_user_id, fk_role_id),
  CONSTRAINT fk_user_role_app_user FOREIGN KEY (fk_user_id) REFERENCES app_user (id),
  CONSTRAINT fk_user_role_role FOREIGN KEY (fk_role_id) REFERENCES role (id)
);