INSERT INTO ow_claim(name, api_http_method, api_endpoint, status)
VALUES ('GetUsersAuthorities', 'GET', '/api/v1/users/authority', 'CREATED'),
       ('GetUsersAuthoritiesById', 'GET', '/api/v1/users/authority/{uuid}', 'CREATED'),
       ('GetCurrentUserSettings', 'GET', '/api/v1/settings', 'CREATED')
ON CONFLICT (name) DO NOTHING;

INSERT INTO module(name, status)
VALUES ('Default', 'CREATED')
ON CONFLICT (name) DO NOTHING;

INSERT INTO module_features(fk_module_id, name, status)
VALUES ((SELECT id FROM module WHERE name = 'Default'), 'Default', 'CREATED')
ON CONFLICT (name) DO NOTHING;

SELECT map_claims_to_feature(
                   (SELECT id FROM module_features WHERE name = 'Default'),
                   array [
                       row ('VIEW', array ['GetUsersAuthorities', 'GetUsersAuthoritiesById', 'GetCurrentUserSettings'])
                       ]::feature_claims[]
           );

INSERT INTO role_features(fk_role_id, fk_module_features_id, feature_action, created_by)
SELECT DISTINCT r.id, mfc.fk_module_features_id, mfc.feature_action, -1
FROM ow_role r,
     module_features_claim mfc
WHERE r.name = 'DEFAULT'
  AND mfc.fk_module_features_id = (SELECT id FROM module_features WHERE name = 'Default')
ORDER BY r.id, mfc.fk_module_features_id
ON CONFLICT (fk_role_id, fk_module_features_id, feature_action) DO NOTHING;