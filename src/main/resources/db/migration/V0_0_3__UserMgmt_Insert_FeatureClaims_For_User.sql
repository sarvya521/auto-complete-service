INSERT INTO claim(name, api_http_method, api_endpoint, status)
VALUES ('UserGetAll', 'GET', '/api/v1/user', 'CREATED', 1),
       ('UserCreate', 'POST', '/api/v1/user', 'CREATED', -1),
       ('UserUpdate', 'PUT', '/api/v1/user', 'CREATED', -1),
       ('UserGetById', 'GET', '/api/v1/user/{uuid}', 'CREATED', -1),
       ('UserDelete', 'DELETE', '/api/v1/user/{uuid}', 'CREATED', -1),

       ('PermissionGetAll', 'GET', '/api/v1/claim', 'CREATED', -1),

       ('RoleGetAll', 'GET', '/api/v1/role', 'CREATED', -1),
       ('RoleCreate', 'POST', '/api/v1/role', 'CREATED', -1),
       ('RoleUpdate', 'PUT', '/api/v1/role', 'CREATED', -1),
       ('RoleDelete', 'DELETE', '/api/v1/role/{uuid}', 'CREATED', -1),
       ('RoleUpdateStatus', 'PUT', '/api/v1/role/status/{action}', 'CREATED')
ON CONFLICT (name) DO NOTHING;

INSERT INTO module(name, status)
VALUES ('Administration', 'CREATED')
ON CONFLICT (name) DO NOTHING;

INSERT INTO module_features(fk_module_id, name, status)
VALUES ((SELECT id FROM module WHERE name = 'Administration'), 'Role', 'CREATED')
ON CONFLICT (name) DO NOTHING;

SELECT map_claims_to_feature(
                   (SELECT id FROM module_features WHERE name = 'Role'),
                   array [
                       row ('VIEW', array ['RoleGetAll']),
                       row ('CREATE', array ['RoleCreate', 'PermissionGetAll']),
                       row ('UPDATE', array ['RoleUpdate', 'RoleUpdateStatus', 'PermissionGetAll']),
                       row ('DELETE', array ['RoleDelete'])
                       ]::feature_claims[]
           );

INSERT INTO role_features(fk_role_id, fk_module_features_id, feature_action, created_by)
SELECT DISTINCT r.id, mfc.fk_module_features_id, mfc.feature_action, -1
FROM ow_role r,
     module_features_claim mfc
WHERE r.name in ('Super Admin')
  AND mfc.fk_module_features_id = (SELECT id FROM module_features WHERE name = 'Role')
ORDER BY r.id, mfc.fk_module_features_id
ON CONFLICT (fk_role_id, fk_module_features_id, feature_action) DO NOTHING;

INSERT INTO module_features(fk_module_id, name, status)
VALUES ((SELECT id FROM module WHERE name = 'Administration'), 'User', 'CREATED')
ON CONFLICT (name) DO NOTHING;

SELECT map_claims_to_feature(
                   (SELECT id FROM module_features WHERE name = 'User'),
                   array [
                       row ('VIEW', array ['UserGetAll','UserGetById']),
                       row ('CREATE', array ['UserCreate', 'RoleGetAll']),
                       row ('UPDATE', array ['UserUpdate', 'UserUpdateStatus', 'RoleGetAll']),
                       row ('DELETE', array ['UserDelete'])
                       ]::feature_claims[]
           );

INSERT INTO role_features(fk_role_id, fk_module_features_id, feature_action, created_by)
SELECT DISTINCT r.id, mfc.fk_module_features_id, mfc.feature_action, -1
FROM ow_role r,
     module_features_claim mfc
WHERE r.name in ('Super Admin')
  AND mfc.fk_module_features_id = (SELECT id FROM module_features WHERE name = 'User')
ORDER BY r.id, mfc.fk_module_features_id
ON CONFLICT (fk_role_id, fk_module_features_id, feature_action) DO NOTHING;