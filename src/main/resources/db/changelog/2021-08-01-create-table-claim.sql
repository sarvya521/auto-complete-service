CREATE TABLE IF NOT EXISTS claim (
  id BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID(), TRUE)) PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  api_http_method VARCHAR(50) NOT NULL,
  api_endpoint VARCHAR(50) NOT NULL,
  ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT uk_claim_name UNIQUE (name)
);