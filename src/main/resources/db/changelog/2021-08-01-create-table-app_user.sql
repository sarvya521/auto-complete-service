-- liquibase formatted sql
-- changeset Sarvesh:create-table-app_user splitStatements:true

/*
 * - Every table has UUID along with its primary key.
 *    We shall not share ID(primary key) in API response, instead we should share UUID for particular entity
 *
 * - Every table has history table for auditing purpose. Master table and mapping tables have no history table
 *   History table will store extra columns like status, and performed_by
 *
 * - No UUID in mapping table
 *
 * - `status` is Enum
 * - `performed_by` (id of user whoever inserting/updating/deleting record in table)
 * - `ts` is TIMESTAMPT column which holds UTC timestamp
 *
 * - No foreign key constraints in history table
 */

/*
 * For every DML(insert,update,delete) action,
 * We will perform DML (One Insert in History and DML on original table)
 *  - insert into history table with latest status and current timestamp
 *  - then we will perform similar DML on original table
 * --------------------------------------------------------------------------------
 * Case 1: insert into user (insert bruce user)
 *
 *
 * user_history
 * id   uuid              name          status    performed_by    ts
 * 1    aasad-asas-asas   bruce         CREATED   10000           2019-10-10 10:10:10
 *
 * user
 * id   uuid              name          status    performed_by    ts
 * 1    aasad-asas-asas   bruce         CREATED   10000           2019-10-10 10:10:10
 * ---------------------------------------------------------------------------------
 * Case 2: update user (Update name of sarvesh)
 *
 * user_history
 * id   uuid              name          status    performed_by    ts
 * 1    aasad-asas-asas   bruce         CREATED   10000           2019-10-10 10:10:10
 * 1    aasad-asas-asas   batman        UPDATED   10001           2019-11-11 10:10:10
 *
 * user
 * id   uuid              name          status    performed_by    ts
 * 1    aasad-asas-asas   batman        UPDATED   10001           2019-11-11 10:10:10
 */

CREATE TABLE IF NOT EXISTS app_user (
  id BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID(), TRUE)) PRIMARY KEY,
  email VARCHAR(255) NOT NULL,
  f_name VARCHAR(50) NOT NULL,
  l_name VARCHAR(50) NOT NULL,
  status ENUM("CREATED","UPDATED","DEACTIVATED","REACTIVATED") NOT NULL,
  ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  version INT NOT NULL,
  CONSTRAINT uk_app_user_email UNIQUE(email)
);

CREATE TABLE IF NOT EXISTS app_user_history (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id binary(16) NOT NULL,
  email VARCHAR(255) NOT NULL,
  f_name VARCHAR(50) NOT NULL,
  l_name VARCHAR(50) NOT NULL,
  status ENUM("CREATED","UPDATED","DEACTIVATED","REACTIVATED") NOT NULL,
  performed_by VARCHAR(255) NOT NULL,
  ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  version INT NOT NULL
);