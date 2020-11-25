INSERT INTO ow_claim(name, api_http_method, api_endpoint, status)
VALUES ('UserGetAuthorities', 'GET', '/api/v1/user/authority', 'CREATED'),
       ('UserGetAuthoritiesById', 'GET', '/api/v1/user/authority/{uuid}', 'CREATED')
ON CONFLICT (name) DO NOTHING;

-- Map User Profile claims to Default Role
INSERT INTO ow_role_claim(fk_role_id, fk_claim_id, created_by)
SELECT r.id, c.id, -1
FROM ow_role r,
     ow_claim c
WHERE r.name = 'DEFAULT'
  AND c.name IN ('UserGetAuthorities', 'UserGetAuthoritiesById')
ON CONFLICT (fk_role_id, fk_claim_id) DO NOTHING;
