CREATE TABLE IF NOT EXISTS car(
    car_id bigserial PRIMARY KEY NOT NULL,
    make varchar NOT NULL,
    model varchar NOT NULL,
    year_of_release smallint NOT NULL
);