import { useState, useEffect } from "react";
import "./App.css";

const API_BASE = "http://localhost:8080";

// API call logger
const logApiCall = (method, endpoint, data = null, response = null, error = null) => {
  const timestamp = new Date().toLocaleTimeString();
  const logEntry = {
    timestamp,
    method,
    endpoint,
    data,
    response,
    error,
    status: error ? "FAILED" : "SUCCESS"
  };
  console.log(`[${timestamp}] ${method} ${endpoint}`, logEntry);
  window.apiLogs = window.apiLogs || [];
  window.apiLogs.push(logEntry);
};

export default function App() {
  const [nodes, setNodes] = useState([]);
  const [loading, setLoading] = useState(false);
  const [simulationRunning, setSimulationRunning] = useState(false);

  useEffect(() => {
    fetchNodes();

    //svakih 100ms da se updatea samo load svakog nodea
    const interval = setInterval(fetchNodeLoads, 100); 
    return () => clearInterval(interval);
  }, []);

  // fetchanje sa backenda
  const fetchNodes = async () => {
    try {
      setLoading(true);
      const response = await fetch(`${API_BASE}/nodeworker/all`);
      const data = await response.json();
      logApiCall("GET", "/nodeworker/all", null, data);
      const normalized = (data || []).map((n, idx) => ({
        id: n.id ?? n.nodeNumber ?? n.NodeWorkerNumber ?? idx + 1,
        freq: n.freq ?? n.frequency ?? n.taskFrequency ?? 50,
        length: n.length ?? n.length ?? n.taskLength ?? 50,
        load: n.load ?? n.queue ?? 0,
        capacity: n.capacity ?? 50,
        // za debugging (inace ne treba)
        __raw: n
      }));

      setNodes(normalized);
    } catch (error) {
      logApiCall("GET", "/nodeworker/all", null, null, error.message);
      console.error("Failed to fetch nodes:", error);
    } finally {
      setLoading(false);
    }
  };

  const fetchNodeLoads = async() => {
    try {
      const response = await fetch(`${API_BASE}/nodereceiver/load`);
      const data = await response.json();
      logApiCall("GET", "/nodereceiver/load", null, data);
      setNodes(prevNodes =>
      prevNodes.map((node, idx) => ({
        ...node,
        load: data[idx] ?? node.load ?? 0 // keep old load if missing
      }))
    );
    }

    catch(error) {
      console.error("Failed to fetch node loads:", error)
    }
  }

  // helper za normalizaciju node podataka iz razlicitih endpointa
  const normalizeNodes = (data) =>
    (data || []).map((n, idx) => ({
      id: n.id ?? n.nodeNumber ?? n.NodeWorkerNumber ?? idx + 1,
      freq: n.freq ?? n.frequency ?? n.taskFrequency ?? 50,
      length: n.length ?? n.length ?? n.taskLength ?? 50,
      load: n.load ?? n.queue ?? 0,
      __raw: n
    }));

  // parser koji pokusava vratiti json, a ako ne moze vrati text
  const tryParse = async (resp) => {
    if (!resp) return null;
    try {
      const text = await resp.text();
      try {
        return JSON.parse(text);
      } catch {
        return text;
      }
    } catch (err) {
      return null;
    }
  };

  const addNode = async () => {
    try {
      setLoading(true);
      const respTG = await fetch(`${API_BASE}/taskgenerator/add`, { method: "POST" });
      const dataTG = await tryParse(respTG);
      logApiCall("POST", "/taskgenerator/add", null, dataTG);

      const respNR = await fetch(`${API_BASE}/nodereceiver/add`, { method: "POST" });
      const dataNR = await tryParse(respNR);
      logApiCall("POST", "/nodereceiver/add", null, dataNR);

      const respNW = await fetch(`${API_BASE}/nodeworker/add`, { method: "POST" });
      const dataNW = await tryParse(respNW);
      logApiCall("POST", "/nodeworker/add", null, dataNW);

      // nodeworker response koristimo za update lokalnog stanja
      try {
        const normalized = normalizeNodes(Array.isArray(dataNW) ? dataNW : dataNW || []);
        setNodes(normalized);
      } catch (err) {
        console.warn("Could not normalize nodeworker/add response, refetching as fallback", err);
        await fetchNodes();
      }
    } catch (error) {
      logApiCall("POST", "/nodeworker/add", null, null, error.message);
      console.error("Failed to add node:", error);
    } finally {
      setLoading(false);
    }
  };

  const updateNode = async (id, field, value) => {
    const numValue = Number(value);

    // odmah update lokalnog stanja
    setNodes(
      nodes.map((n) =>
        n.id === id ? { ...n, [field]: numValue } : n
      )
    );

    // onda se salje update na backend
    try {
      //pomoćna mapa preko koje se skonta što updateamo u nodeu
      const fieldMap = {
      freq: { endpoint: "/taskgenerator/frequency", key: "frequency" },
      length: { endpoint: "/taskgenerator/length", key: "length" },
      capacity: { endpoint: "/nodereceiver/capacity", key: "capacity" }
    };

    if(!fieldMap[field]) throw new Error(`Unknown field: ${field}`);

    const {endpoint, key} = fieldMap[field];

    const requestBody = {
      nodeNumber: id,
      [key]: numValue
    };

    const response = await fetch(`${API_BASE}${endpoint}`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(requestBody)
    });
    
    const data = await tryParse(response);
    
    logApiCall(`POST`, endpoint, requestBody, data);
  } catch (error) {
    logApiCall("POST", field === "freq" ? "/taskgenerator/frequency" : "/taskgenerator/length", 
                { nodeNumber: id }, null, error.message);
    console.error(`Failed to update ${field}:`, error);
    }
  };

  const deleteNode = async (id) => {
    try {
      setLoading(true);
      const respTG = await fetch(`${API_BASE}/taskgenerator/delete/${id}`, { method: "DELETE" });
      const dataTG = await tryParse(respTG);
      logApiCall("DELETE", `/taskgenerator/delete/${id}`, null, dataTG);

      const respNR = await fetch(`${API_BASE}/nodereceiver/delete/${id}`, { method: "DELETE" });
      const dataNR = await tryParse(respNR);
      logApiCall("DELETE", `/nodereceiver/delete/${id}`, null, dataNR);

      const respNW = await fetch(`${API_BASE}/nodeworker/delete/${id}`, { method: "DELETE" });
      const dataNW = await tryParse(respNW);
      logApiCall("DELETE", `/nodeworker/delete/${id}`, null, dataNW);

      try {
        const normalized = normalizeNodes(Array.isArray(dataNW) ? dataNW : dataNW || []);
        setNodes(normalized);
      } catch (err) {
        console.warn("Could not normalize nodeworker/delete response, refetching as fallback", err);
        await fetchNodes();
      }
    } catch (error) {
      logApiCall("DELETE", `/nodeworker/delete/${id}`, null, null, error.message);
      console.error("Failed to delete node:", error);
    } finally {
      setLoading(false);
    }
  };

  const startSimulation = async () => {
    try {
      //prvo starta task generatore
    let response = await fetch(`${API_BASE}/taskgenerator/startall`, {
      method: "POST",
      headers: { "Content-Type": "application/json" }
    });
    let data = await tryParse(response);
    logApiCall("POST", "/taskgenerator/startall", null, data);

      //onda starta node receivere
    response = await fetch(`${API_BASE}/nodereceiver/startall`, {
      method: "POST",
      headers: { "Content-Type": "application/json" }
    });
    data = await tryParse(response);
    logApiCall("POST", "/nodereceiver/startall", null, data);

    //onda starta node workere
    response = await fetch(`${API_BASE}/nodeworker/startall`, {
      method: "POST",
      headers: { "Content-Type": "application/json" }
    });
    data = await tryParse(response);
    logApiCall("POST", "/nodeworker/startall", null, data)

    setSimulationRunning(true);
    } catch (error) {
      logApiCall("POST", "/nodeworker/startall", null, null, error.message);
      console.error("Failed to start simulation:", error);
    }
  };

  const stopSimulation = async () => {
    try {
      let response = await fetch(`${API_BASE}/taskgenerator/stopall`, {
        method: "POST",
        headers: { "Content-Type": "application/json" }
      });
      let data = await tryParse(response);
      logApiCall("POST", "/nodeworker/stopall", null, data);

      response = await fetch(`${API_BASE}/taskgenerator/stopall`, {
        method: "POST",
        headers: { "Content-Type": "application/json" }
      });
      data = await tryParse(response);
      logApiCall("POST", "/nodereceiver/startall", null, data);

      response = await fetch(`${API_BASE}/nodeworker/stopall`, {
        method: "POST",
        headers: { "Content-Type": "application/json" }
      });
      data = await tryParse(response);
      logApiCall("POST", "/nodeworker/stopall", null, data);

      setSimulationRunning(false);
    } catch (error) {
      logApiCall("POST", "/nodeworker/stopall", null, null, error.message);
      console.error("Failed to stop simulation:", error);
    }
  };

  return (
    <div className="container">
      <div className="button-bar">
        <button 
          className="start sim-btn" 
          onClick={startSimulation}
          disabled={loading || simulationRunning}
        >
          Start Simulation
        </button>
        <button 
          className="stop sim-btn"
          onClick={stopSimulation}
          disabled={loading || !simulationRunning}
        >
          Stop Simulation
        </button>
        <button className="pause sim-btn" disabled>Pause Simulation</button>
      </div>

      {loading && <div style={{ padding: "10px", color: "#666" }}>Loading...</div>}

      {nodes.length === 0 ? (
        <div style={{ padding: "20px", textAlign: "center", color: "#999" }}>
          No nodes yet. Click "Add Node" to get started.
        </div>
      ) : (
        <table className="node-table">
          <thead>
            <tr>
              <th>Node</th>
              <th>Task Frequency</th>
              <th>Task Length</th>
              <th>Queue / Load</th>
              <th>Capacity</th>
              <th></th>
            </tr>
          </thead>

          <tbody>
            {nodes.map((node, idx) => {
              const displayId = node.id ?? idx + 1;
              return (
                <tr key={displayId} className="node-row">
                  <td>Node #{displayId}</td>

                  <td>
                    <input
                      type="range"
                      min="1"
                      max="100"
                      value={node.freq ?? 50}
                      onChange={(e) =>
                        updateNode(displayId, "freq", e.target.value)
                      }
                      className="slider"
                    />
                  </td>

                  <td>
                    <input
                      type="range"
                      min="1"
                      max="100"
                      value={node.length ?? 50}
                      onChange={(e) =>
                        updateNode(displayId, "length", e.target.value)
                      }
                      className="slider"
                    />
                  </td>

                  <td>{(node.load * 100).toFixed(0) ?? 0}%</td>

                  <td>
                    <input
                      type="number"
                      step="1"
                      min="1"
                      value={node.capacity ?? 50}
                      inputmode="numeric"
                      pattern="[0-9]*"
                      style={{width: "6ch"}}
                      onChange={(e) =>
                        updateNode(displayId, "capacity", e.target.value)
                      }
                      />
                  </td>

                  <td>
                    <button
                      className="trash-btn"
                      onClick={() => deleteNode(displayId)}
                      title={`Delete node ${displayId}`}
                      disabled={loading}
                    >
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      viewBox="0 0 24 24"
                      width="20"
                      height="20"
                      fill="currentColor"
                    >
                      <path d="M9 3V4H4V6H5V20C5 21.1 5.9 22 7 22H17C18.1 22 19 21.1 19 20V6H20V4H15V3H9M7 6H17V20H7V6Z" />
                    </svg>
                    </button>
                  </td>
                </tr>
              );
            })}
          </tbody>
        </table>
      )}

      <div className="add-node-container">
        <button 
          className="add-node-btn" 
          onClick={addNode}
          disabled={loading}
        >
          Add Node
        </button>
      </div>
    </div>
  );
}
