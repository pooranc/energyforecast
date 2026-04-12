CREATE TABLE IF NOT EXISTS energy_reading (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    timestamp   TIMESTAMPTZ NOT NULL,
    value       DOUBLE PRECISION NOT NULL,
    unit        VARCHAR(10) NOT NULL,
    source      VARCHAR(50) NOT NULL
);