ALTER TABLE counter_model
ADD COLUMN user_model_id bigint;


ALTER TABLE counter_model
ADD CONSTRAINT fk_user_model FOREIGN KEY(user_model_id) REFERENCES user_model(id) ON DELETE SET NULL;
