-- liquibase formatted sql
-- changeset Sarvesh:insert-default-role-with-default-claims splitStatements:true

INSERT INTO claim(name, api_http_method, api_endpoint)
VALUES ('UserGetAll', 'GET', '/api/v1/user'),
       ('UserCreate', 'POST', '/api/v1/user'),
       ('UserUpdate', 'PUT', '/api/v1/user'),
       ('UserGetById', 'GET', '/api/v1/user/{uuid}'),
       ('UserDelete', 'DELETE', '/api/v1/user/{uuid}'),
       ('PermissionGetAll', 'GET', '/api/v1/claim'),
       ('RoleGetAll', 'GET', '/api/v1/role'),
       ('RoleCreate', 'POST', '/api/v1/role'),
       ('RoleUpdate', 'PUT', '/api/v1/role'),
       ('RoleDelete', 'DELETE', '/api/v1/role/{uuid}'),
       ('RoleUpdateStatus', 'PUT', '/api/v1/role/status/{action}')
ON DUPLICATE KEY UPDATE name=name;

INSERT INTO module(name, status)
VALUES ('Administration', 'CREATED')
ON DUPLICATE KEY UPDATE name=name;

-- liquibase formatted sql
-- changeset Sarvesh:insert-featureClaims-user splitStatements:true

INSERT INTO module_features(fk_module_id, name, status)
VALUES ((SELECT id FROM module WHERE name = 'Administration'), 'Role', 'CREATED')
ON DUPLICATE KEY UPDATE name=name;

CALL map_claims_to_feature(
  (SELECT id FROM module_features WHERE name = 'Role'),
  '[
    {"featureAction" : "VIEW", "claims": ["RoleGetAll"]},
    {"featureAction" : "CREATE", "claims": ["RoleCreate","PermissionGetAll"]},
    {"featureAction" : "UPDATE", "claims": ["RoleUpdate","RoleUpdateStatus","PermissionGetAll"]},
    {"featureAction" : "DELETE", "claims": ["RoleDelete"]}
  ]'
);

INSERT INTO role_features(fk_role_id, fk_module_features_id, feature_action, performed_by)
SELECT DISTINCT r.id, mfc.fk_module_features_id, mfc.feature_action, 'SYS'
FROM role r,
     module_features_claim mfc
WHERE r.name in ('Super Admin')
  AND mfc.fk_module_features_id = (SELECT id FROM module_features WHERE name = 'Role')
ON DUPLICATE KEY UPDATE fk_role_id=VALUES(fk_role_id), fk_module_features_id=VALUES(fk_module_features_id), feature_action=VALUES(feature_action);

INSERT INTO module_features(fk_module_id, name, status)
VALUES ((SELECT id FROM module WHERE name = 'Administration'), 'User', 'CREATED')
ON DUPLICATE KEY UPDATE name=name;

CALL map_claims_to_feature(
  (SELECT id FROM module_features WHERE name = 'User'),
  '[
    {"featureAction" : "VIEW", "claims": ["UserGetAll"]},
    {"featureAction" : "CREATE", "claims": ["UserCreate","RoleGetAll"]},
    {"featureAction" : "UPDATE", "claims": ["UserUpdate","RoleGetAll"]},
    {"featureAction" : "DELETE", "claims": ["UserDelete"]}
  ]'
);

INSERT INTO role_features(fk_role_id, fk_module_features_id, feature_action, performed_by)
SELECT DISTINCT r.id, mfc.fk_module_features_id, mfc.feature_action, 'SYS'
FROM role r,
     module_features_claim mfc
WHERE r.name in ('Super Admin')
  AND mfc.fk_module_features_id = (SELECT id FROM module_features WHERE name = 'User')
ON DUPLICATE KEY UPDATE fk_role_id=VALUES(fk_role_id), fk_module_features_id=VALUES(fk_module_features_id), feature_action=VALUES(feature_action);