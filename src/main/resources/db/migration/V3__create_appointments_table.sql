CREATE TABLE appointments
(
    id              BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    customer_id     BIGINT    NOT NULL REFERENCES customers (id),
    professional_id BIGINT    NOT NULL REFERENCES professionals (id),
    start_time      TIMESTAMP NOT NULL,
    end_time        TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL,
    CHECK (end_time > start_time)
);