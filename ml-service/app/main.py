from fastapi import FastAPI
from app.schemas import PredictRequest, PredictResponse
from app.train import train
import numpy as np


app = FastAPI (title = "EnergyForecast ML Service")

@app.get("/health")
def health():
    return {"status": "ok"}

@app.post("/predict", response_model=PredictResponse)
def predict(req: PredictRequest):
    model, version = train(req.source, req.readings)

    last_values = [r.value for r in req.readings[-6:]]
    while len(last_values) < 6:
        last_values.insert(0, last_values[0])

    predictions = []
    window = last_values.copy()

    for _ in range (req.horizon_hours):
        features = np.array([[window[-1], window[-2], window[-3], window[-4]]])
        next_val = model.predict(features)[0]
        predictions.append(round(float(next_val), 3))
        window.append(next_val)

    return PredictResponse(
        predicted_values=predictions,
        model_version=version
    )