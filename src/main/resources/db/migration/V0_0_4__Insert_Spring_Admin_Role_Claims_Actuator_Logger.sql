INSERT INTO ow_claim(name, api_http_method, api_endpoint, status)
VALUES ('LoggerLevelGet', 'GET',
        '/api/v1/boilerplate/actuator/loggers/com.backend.boilerplate', 'CREATED'),
       ('LoggerLevelUpdate', 'POST',
        '/api/v1/boilerplate/actuator/loggers/com.backend.boilerplate', 'CREATED')
ON CONFLICT (name) DO NOTHING;

INSERT INTO ow_role_claim(fk_role_id, fk_claim_id, created_by)
SELECT r.id, c.id, -1
FROM ow_role r,
     ow_claim c
WHERE r.name = 'Spring Admin'
  AND c.name IN ('LoggerLevelGet', 'LoggerLevelUpdate')
ON CONFLICT (fk_role_id, fk_claim_id) DO NOTHING;
