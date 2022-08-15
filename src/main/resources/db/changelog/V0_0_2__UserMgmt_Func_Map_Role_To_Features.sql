DO
$$
    BEGIN
        IF NOT EXISTS(SELECT 1 FROM pg_type WHERE typname = 'feature_claims') THEN
            CREATE TYPE feature_claims AS
            (
                featureAction feature_action,
                claims        VARCHAR[]
            );
        END IF;
    END
$$;

CREATE OR REPLACE FUNCTION map_claims_to_feature(feature_id BIGINT, feature_claims_arr IN feature_claims[])
    RETURNS VOID
AS
$$
DECLARE
    fc                    feature_claims;
    DECLARE featureAction feature_action;
    DECLARE claims        VARCHAR[];
    DECLARE claim_name    VARCHAR(1000);
    DECLARE claim_id      BIGINT;
BEGIN
    FOREACH fc in array feature_claims_arr
        LOOP
            featureAction := fc.featureAction;
            claims := fc.claims;
            FOREACH claim_name IN ARRAY claims
                LOOP
                    claim_id := (SELECT id FROM claim WHERE name = claim_name);
                    INSERT INTO module_features_claim(fk_module_features_id, feature_action, fk_claim_id)
                    VALUES (feature_id, featureAction, claim_id)
                    ON CONFLICT (fk_module_features_id, feature_action, fk_claim_id) DO NOTHING;
                END LOOP;
        END LOOP;
END;
$$
    LANGUAGE PLPGSQL;
--
-- SELECT map_claims_to_feature(
--                    (SELECT id FROM module_features WHERE name = 'XYZ'),
--                    array [
--                        row ('VIEW', array ['AbcGetAll','AbcGetById']),
--                        row ('CREATE', array ['AbcCreate']),
--                        row ('UPDATE', array ['AbcUpdate','AbcPatch']),
--                        row ('DELETE', array ['AbcDelete'])
--                        ]::feature_claims[]
--            );