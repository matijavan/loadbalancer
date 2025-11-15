import { useState } from "react";
import "./App.css";

export default function App() {
  const [nodes, setNodes] = useState([
    { id: 1, freq: 50, length: 50, load: 0 }
  ]);

  const addNode = () => {
    const nextId = nodes.length + 1;
    setNodes([
      ...nodes,
      { id: nextId, freq: 50, length: 50, load: 0 }
    ]);
  };

  const updateNode = (id, field, value) => {
    setNodes(
      nodes.map((n) =>
        n.id === id ? { ...n, [field]: Number(value) } : n
      )
    );
  };

const deleteNode = (id) => {
  const filtered = nodes.filter(n => n.id !== id);

  // Reassign IDs sequentially (1, 2, 3, ...)
  const reindexed = filtered.map((n, index) => ({
    ...n,
    id: index + 1
  }));

  setNodes(reindexed);
};


  return (
    <div className="container">
      {/* Simulation Buttons */}
      <div className="button-bar">
        <button className="start sim-btn">Start Simulation</button>
        <button className="stop sim-btn">Stop Simulation</button>
        <button className="pause sim-btn">Pause Simulation</button>
      </div>

      {/* Table and external trash column side-by-side */}
        <table className="node-table">
          <thead>
            <tr>
              <th>Node</th>
              <th>Task Frequency</th>
              <th>Task Length</th>
              <th>Queue / Load</th>
              <th></th>
            </tr>
          </thead>

          <tbody>
            {nodes.map((node) => (
              <tr key={node.id} className="node-row">
                <td>Node #{node.id}</td>

                <td>
                  <input
                    type="range"
                    min="1"
                    max="100"
                    value={node.freq}
                    onChange={(e) =>
                      updateNode(node.id, "freq", e.target.value)
                    }
                    className="slider"
                  />
                </td>

                <td>
                  <input
                    type="range"
                    min="1"
                    max="100"
                    value={node.length}
                    onChange={(e) =>
                      updateNode(node.id, "length", e.target.value)
                    }
                    className="slider"
                  />
                </td>

                <td>{node.load}%</td>

                {/* Delete button now back inside the table */}
                <td>
                  <button
                    className="trash-btn"
                    onClick={() => deleteNode(node.id)}
                    title={`Delete node ${node.id}`}
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
            ))}
          </tbody>
        </table>
      {/* Add Node placed outside the table but visually below it */}
      <div className="add-node-container">
        <button className="add-node-btn" onClick={addNode}>
          Add Node
        </button>
      </div>
    </div>
  );
}
