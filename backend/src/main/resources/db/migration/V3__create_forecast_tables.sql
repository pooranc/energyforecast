CREATE TABLE IF NOT EXISTS forecast_result (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    source VARCHAR(50) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    horizon_hours INT,
    model_version VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS forecast_result_predicted_values (
    forecast_result_id UUID REFERENCES forecast_result(id),
    predicted_values   DOUBLE PRECISION
);
