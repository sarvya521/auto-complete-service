INSERT INTO claim(name, api_http_method, api_endpoint, status)
VALUES ('UserGetAll', 'GET', '/api/v1/user', 'CREATED'),
       ('UserCreate', 'POST', '/api/v1/user', 'CREATED'),
       ('UserUpdate', 'PUT', '/api/v1/user', 'CREATED'),
       ('UserGetById', 'GET', '/api/v1/user/{uuid}', 'CREATED'),
       ('UserDelete', 'DELETE', '/api/v1/user/{uuid}', 'CREATED'),

       ('PermissionGetAll', 'GET', '/api/v1/claim', 'CREATED'),

       ('RoleGetAll', 'GET', '/api/v1/role', 'CREATED'),
       ('RoleCreate', 'POST', '/api/v1/role', 'CREATED'),
       ('RoleUpdate', 'PUT', '/api/v1/role', 'CREATED'),
       ('RoleDelete', 'DELETE', '/api/v1/role/{uuid}', 'CREATED'),
       ('RoleUpdateStatus', 'PUT', '/api/v1/role/status/{action}', 'CREATED')
ON DUPLICATE KEY UPDATE name=name;

INSERT INTO module(name, status)
VALUES ('Administration', 'CREATED')
ON DUPLICATE KEY UPDATE name=name;

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

INSERT INTO role_features(fk_role_id, fk_module_features_id, feature_action, created_by)
SELECT DISTINCT r.id, mfc.fk_module_features_id, mfc.feature_action, -1
FROM role r,
     module_features_claim mfc
WHERE r.name in ('Super Admin')
  AND mfc.fk_module_features_id = (SELECT id FROM module_features WHERE name = 'Role')
ORDER BY r.id, mfc.fk_module_features_id
ON DUPLICATE KEY UPDATE fk_role_id=fk_role_id, fk_module_features_id=fk_module_features_id, feature_action=feature_action;

INSERT INTO module_features(fk_module_id, name, status)
VALUES ((SELECT id FROM module WHERE name = 'Administration'), 'User', 'CREATED')
ON DUPLICATE KEY UPDATE name=name;

CALL map_claims_to_feature(
  (SELECT id FROM module_features WHERE name = 'User'),
  '[
    {"featureAction" : "VIEW", "claims": ["UserGetAll","UserGetAll"]},
    {"featureAction" : "CREATE", "claims": ["UserCreate","RoleGetAll"]},
    {"featureAction" : "UPDATE", "claims": ["UserUpdate","UserUpdateStatus","RoleGetAll"]},
    {"featureAction" : "DELETE", "claims": ["UserDelete"]}
  ]'
);

INSERT INTO role_features(fk_role_id, fk_module_features_id, feature_action, created_by)
SELECT DISTINCT r.id, mfc.fk_module_features_id, mfc.feature_action, -1
FROM role r,
     module_features_claim mfc
WHERE r.name in ('Super Admin')
  AND mfc.fk_module_features_id = (SELECT id FROM module_features WHERE name = 'User')
ORDER BY r.id, mfc.fk_module_features_id
ON DUPLICATE KEY UPDATE fk_role_id=fk_role_id, fk_module_features_id=fk_module_features_id, feature_action=feature_action;