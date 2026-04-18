# EnergyForecast Service

A full-stack energy forecasting system that collects real-time energy readings, processes them through a Kafka pipeline, and generates ML-powered forecasts using a Python microservice tracked with MLflow.

## Architecture

React Dashboard → Spring Boot API → PostgreSQL
→ Kafka → ML Service (FastAPI + scikit-learn)
→ MLflow Tracking

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 21, Spring Boot 3.5, JPA, Flyway, Kafka |
| ML Service | Python 3.11, FastAPI, scikit-learn, MLflow |
| Frontend | React 18, Vite, Recharts |
| Database | PostgreSQL 16 |
| Messaging | Apache Kafka |
| Infra | Docker Compose, GitHub Actions |

## Prerequisites

- Java 21
- Python 3.11
- Node 20
- Docker Desktop

## Quick Start

```bash
# 1. Start infrastructure
docker compose up -d

# 2. Start Spring Boot (IntelliJ or)
cd backend && mvn spring-boot:run

# 3. Start ML service
cd ml-service
python -m venv venv && venv/Scripts/activate
pip install -r requirements.txt
uvicorn app.main:app --port 8000

# 4. Start frontend
cd frontend && npm install && npm run dev
```

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | /api/readings | Submit energy reading |
| GET | /api/readings?source=solar | Get readings by source |
| GET | /api/forecast?source=solar | Get latest forecast |

## MLflow UI

Open http://localhost:5000 to view experiment runs and model metrics.