INSERT INTO claim(name, api_http_method, api_endpoint, status)
VALUES ('LoggerLevelGet', 'GET',
        '/api/v1/boilerplate/actuator/loggers/com.backend.boilerplate', 'CREATED'),
       ('LoggerLevelUpdate', 'POST',
        '/api/v1/boilerplate/actuator/loggers/com.backend.boilerplate', 'CREATED')
ON DUPLICATE KEY UPDATE name=name;

INSERT INTO role_claim(fk_role_id, fk_claim_id, created_by)
SELECT r.id, c.id, -1
FROM role r,
     claim c
WHERE r.name = 'Spring Admin'
  AND c.name IN ('LoggerLevelGet', 'LoggerLevelUpdate')
ON DUPLICATE KEY UPDATE fk_role_id=fk_role_id, fk_claim_id=fk_claim_id;
