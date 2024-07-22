ALTER TABLE counter_group_model
ALTER COLUMN id TYPE bigint,
ALTER COLUMN id SET not null;

ALTER TABLE counter_model
ALTER COLUMN id TYPE bigint,
ALTER COLUMN id SET not null;

ALTER TABLE counter_reading_model
ALTER COLUMN id TYPE bigint,
ALTER COLUMN id SET not null;