import { useState, useEffect } from "react"
import axios from "axios"
import { LineChart, Line, AreaChart, Area, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from "recharts"
import { format } from "date-fns"

const API = "http://localhost:8080"
const SOURCES = ["solar", "grid", "battery"]

export default function App() {
  const [source, setSource] = useState("solar")
  const [readings, setReadings] = useState([])
  const [forecast, setForecast] = useState(null)
  const [value, setValue] = useState("")

  useEffect(() => {
    fetchReadings()
    fetchForecast()
  }, [source])

  const fetchReadings = async () => {
    const res = await axios.get(`${API}/api/readings?source=${source}`)
    setReadings(res.data)
  }

  const fetchForecast = async () => {
    const res = await axios.get(`${API}/api/forecast?source=${source}`)
    setForecast(res.data[0])
  }

  const submitReading = async () => {
    await axios.post(`${API}/api/readings`, { value: parseFloat(value), unit: "kW", source })
    setValue("")
    fetchReadings()
  }

  return (
    <div style={{ maxWidth: 900, margin: "0 auto", padding: "2rem", fontFamily: "sans-serif" }}>
      <h1 style={{ fontSize: 22, fontWeight: 500 }}>EnergyForecast Dashboard</h1>
      
      <div style={{ display: "flex", gap: 8, margin: "1rem 0" }}>
        {SOURCES.map(s => (
          <button key={s} onClick={() => setSource(s)} style={{
            padding: "6px 16px", borderRadius: 6, border: "1px solid #ccc",
            background: source === s ? "#7F77DD" : "#fff",
            color: source === s ? "#fff" : "#333", cursor: "pointer"
          }}>{s}</button>
        ))}
      </div>

      <div style={{ display: "flex", gap: 8, margin: "1rem 0" }}>
        <input
          type="number" placeholder="Enter kW value" value={value}
          onChange={e => setValue(e.target.value)}
          style={{ padding: "6px 12px", borderRadius: 6, border: "1px solid #ccc", width: 200 }}
        />
        <button onClick={submitReading} style={{
          padding: "6px 16px", borderRadius: 6, border: "none",
          background: "#1D9E75", color: "#fff", cursor: "pointer"
        }}>Add Reading</button>
      </div>

      <h2 style={{ fontSize: 16, fontWeight: 500 }}>Readings — {source}</h2>
     <ResponsiveContainer width="100%" height={250}>
  <LineChart data={readings.map(r => ({ time: format(new Date(r.timestamp), "HH:mm"), value: r.value }))}>
    <CartesianGrid strokeDasharray="3 3" />
    <XAxis dataKey="time" />
    <YAxis unit=" kW" />
    <Tooltip />
    <Line type="monotone" dataKey="value" stroke="#7F77DD" dot={false} strokeWidth={2} />
  </LineChart>
</ResponsiveContainer>

      <h2 style={{ fontSize: 16, fontWeight: 500 }}>Forecast — {source}</h2>
      {forecast && (
  <>
    <p style={{ fontSize: 12, color: "#888" }}>Model: {forecast.modelVersion}</p>
    <ResponsiveContainer width="100%" height={250}>
      <AreaChart data={forecast.predictedValues.map((v, i) => ({ hour: `+${i+1}h`, value: v }))}>
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis dataKey="hour" />
        <YAxis unit=" kW" />
        <Tooltip />
        <Area type="monotone" dataKey="value" stroke="#1D9E75" fill="#E1F5EE" strokeWidth={2} />
      </AreaChart>
    </ResponsiveContainer>
  </>
)}
    </div>
  )
}