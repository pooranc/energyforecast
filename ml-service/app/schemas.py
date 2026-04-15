from pydantic import BaseModel
from typing import List 

class ReadingPoint(BaseModel):
    timestamp: str
    value: float

class PredictRequest(BaseModel):
    source: str
    readings: List[ReadingPoint]
    horizon_hours: int = 24

class PredictResponse(BaseModel):
    predicted_values: List[float]
    model_version: str
