ALTER TABLE counter_reading_model
ALTER COLUMN counter_id TYPE bigint USING counter_id::bigint;

ALTER TABLE counter_reading_model
ALTER COLUMN group_id TYPE bigint USING group_id::bigint;