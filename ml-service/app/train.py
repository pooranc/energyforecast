import mlflow
import mlflow.sklearn
from sklearn.linear_model import Ridge
from sklearn.metrics import mean_absolute_error
import pandas as pd
import numpy as np

MLFLOW_TRACKING_URI = "http://localhost:5000"

def create_lag_freatures(df: pd.DataFrame, lags: list) -> pd.DataFrame:
    for lag in lags:
        df[f"lag_{lag}"] = df["value"].shift(lag)
    return df.dropna()

def train(source: str, readings: list):
    mlflow.set_tracking_uri(MLFLOW_TRACKING_URI)
    mlflow.set_experiment(f"energyforecast-{source}")

    df = pd.DataFrame([{"timestamp": r.timestamp, "value": r.value} for r in readings])
    df = df.sort_values("timestamp").reset_index(drop=True)

    lags = [1,2,3,4]
    alpha = 1.0

    with mlflow.start_run():
        mlflow.log_param("lags", lags)
        mlflow.log_param("alpha", alpha)
        mlflow.log_param("source", source)

        df = create_lag_freatures(df, lags)
        X = df[[f"lag_{1}" for l in lags]]
        y = df["value"]

        model = Ridge(alpha=alpha)
        model.fit(X, y)

        preds = model.predict(X)
        mae = mean_absolute_error(y, preds)
        mlflow.log_metric("mae", mae)

        mlflow.sklearn.log_model(
            model,
            artifact_path="model"
        )

        print(f"Trained model for {source} | MAE: {mae:.3f}")
        return model, f"v1"

