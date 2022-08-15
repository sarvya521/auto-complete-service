INSERT INTO claim(name, api_http_method, api_endpoint, status)
VALUES ('UserGetAuthorities', 'GET', '/api/v1/user/authority', 'CREATED'),
       ('UserGetAuthoritiesById', 'GET', '/api/v1/user/authority/{uuid}', 'CREATED')
ON DUPLICATE KEY UPDATE name=name;

-- Map User Profile claims to Default Role
INSERT INTO role_claim(fk_role_id, fk_claim_id, created_by)
SELECT r.id, c.id, -1
FROM role r,
     claim c
WHERE r.name = 'DEFAULT'
  AND c.name IN ('UserGetAuthorities', 'UserGetAuthoritiesById')
ON DUPLICATE KEY UPDATE fk_role_id=fk_role_id, fk_claim_id=fk_claim_id;
