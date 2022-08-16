-- liquibase formatted sql
-- changeset Sarvesh:insert-default-role-with-default-claims splitStatements:true

INSERT INTO claim(name, api_http_method, api_endpoint)
VALUES ('GetUsersAuthorities', 'GET', '/api/v1/users/authority'),
       ('GetUsersAuthoritiesById', 'GET', '/api/v1/users/authority/{uuid}'),
       ('GetCurrentUserSettings', 'GET', '/api/v1/settings')
ON DUPLICATE KEY UPDATE name=name;

INSERT INTO module(name, status)
VALUES ('Default', 'CREATED')
ON DUPLICATE KEY UPDATE name=name;

INSERT INTO module_features(fk_module_id, name, status)
VALUES ((SELECT id FROM module WHERE name = 'Default'), 'Default', 'CREATED')
ON DUPLICATE KEY UPDATE name=name;

CALL map_claims_to_feature(
  (SELECT id FROM module_features WHERE name = 'Default'),
  '[{"featureAction" : "VIEW", "claims": ["GetUsersAuthorities","GetUsersAuthoritiesById","GetCurrentUserSettings"]}]'
);

INSERT INTO role_features(fk_role_id, fk_module_features_id, feature_action, performed_by)
SELECT DISTINCT r.id, mfc.fk_module_features_id, mfc.feature_action, 'SYS'
FROM role r,
     module_features_claim mfc
WHERE r.name = 'DEFAULT'
  AND mfc.fk_module_features_id = (SELECT id FROM module_features WHERE name = 'Default')
ON DUPLICATE KEY UPDATE fk_role_id=VALUES(fk_role_id), fk_module_features_id=VALUES(fk_module_features_id), feature_action=VALUES(feature_action);