-- liquibase formatted sql
-- changeset Sarvesh:insert-system-admin-role-with-claims splitStatements:true

INSERT INTO claim(name, api_http_method, api_endpoint)
VALUES ('GetAllUsersForCurrentUser', 'GET', '/api/v1/users'),
       ('GetUsersById', 'GET', '/api/v1/users/{uuid}'),
       ('CreateUsers', 'POST', '/api/v1/users'),
       ('UpdateUsers', 'PUT', '/api/v1/users'),
       ('GetAllRolesForCurrentUser', 'GET', '/api/v1/roles'),
       ('GetLoggerLevelUserManagement', 'GET', '/api/v1/boilerplate/actuator/loggers/com.backend.boilerplate'),
       ('GetLoggerLevelUserManagementSpring', 'GET', '/api/v1/boilerplate/actuator/loggers/org.springframework.web'),
       ('UpdateLoggerLevelUserManagement', 'POST', '/api/v1/boilerplate/actuator/loggers/com.backend.boilerplate'),
       ('UpdateLoggerLevelUserManagementSpring', 'POST', '/api/v1/boilerplate/actuator/loggers/org.springframework.web')
ON DUPLICATE KEY UPDATE name=name;

INSERT INTO module(name, status)
VALUES ('Administration', 'CREATED')
ON DUPLICATE KEY UPDATE name=name;

INSERT INTO module_features(fk_module_id, name, status)
VALUES ((SELECT id FROM module WHERE name = 'Administration'), 'System Administration', 'CREATED')
ON DUPLICATE KEY UPDATE name=name;

CALL map_claims_to_feature(
  (SELECT id FROM module_features WHERE name = 'System Administration'),
  '[
    {"featureAction" : "VIEW", "claims": ["GetAllUsersForCurrentUser","GetUsersById","GetAllRolesForCurrentUser","GetLoggerLevelUserManagement","GetLoggerLevelUserManagementSpring"]},
    {"featureAction" : "CREATE", "claims": ["CreateUsers"]},
    {"featureAction" : "UPDATE", "claims": ["UpdateUsers","UpdateLoggerLevelUserManagement","UpdateLoggerLevelUserManagementSpring"]}
  ]'
);

INSERT INTO role_features(fk_role_id, fk_module_features_id, feature_action, performed_by)
SELECT DISTINCT r.id, mfc.fk_module_features_id, mfc.feature_action, 'SYS'
FROM role r,
     module_features_claim mfc
WHERE r.name = 'System Admin'
  AND mfc.fk_module_features_id = (SELECT id FROM module_features WHERE name = 'System Administration')
ON DUPLICATE KEY UPDATE fk_role_id=VALUES(fk_role_id), fk_module_features_id=VALUES(fk_module_features_id), feature_action=VALUES(feature_action);