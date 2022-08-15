-- CALL map_claims_to_feature(
--                    (SELECT id FROM module_features WHERE name = 'XYZ'),
--                    '[
--                        {"featureAction" : "VIEW", "claims": ["AbcGetAll","AbcGetById"]},
--                        {"featureAction" : "CREATE', "claims": ["AbcCreate"]},
--                        {"featureAction" : "UPDATE', "claims": ["AbcUpdate","AbcPatch"]},
--                        {"featureAction" : "DELETE', "claims": ["AbcDelete"]}
--                    ]'
--            );
DELIMITER $$
CREATE PROCEDURE map_claims_to_feature(feature_id BINARY(16), feature_claims_arr JSON)
  BEGIN
    DECLARE `feature_claims` BIGINT UNSIGNED DEFAULT JSON_LENGTH(`feature_claims_arr`);
    DECLARE `_index` BIGINT UNSIGNED DEFAULT 0;
    DECLARE `_claimIndex` BIGINT UNSIGNED DEFAULT 0;
    DECLARE `featureAction` ENUM("VIEW", "CREATE", "UPDATE", "DELETE");
    DECLARE `claims` JSON;
    DECLARE `claim_id` BINARY(16);
    DECLARE `claim_name` VARCHAR(255);

    WHILE `_index` < `feature_claims` DO
        SET `featureAction` := (SELECT JSON_EXTRACT(`feature_claims_arr`, CONCAT('$[', `_index`, '].featureAction')));
        SET `claims` := (SELECT JSON_EXTRACT(`feature_claims_arr`, CONCAT('$[', `_index`, '].claims')));
        SET `_claimIndex` := 0;
        WHILE `_claimIndex` < JSON_LENGTH(`claims`) DO
            SET `claim_name` := (SELECT JSON_EXTRACT(`claims`, CONCAT('$[', `_claimIndex`, ']')));
            SET `claim_id` := (SELECT id FROM claim WHERE name = `claim_name`);
            INSERT INTO module_features_claim(fk_module_features_id, feature_action, fk_claim_id)
            VALUES (feature_id, featureAction, claim_id)
            ON DUPLICATE KEY UPDATE fk_module_features_id=fk_module_features_id, feature_action=feature_action, fk_claim_id=fk_claim_id;
            SET `_claimIndex` := `_claimIndex` + 1;
        END WHILE;
        SET `_index` := `_index` + 1;
    END WHILE;
END $$