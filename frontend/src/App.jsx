/* eslint-disable react-hooks/set-state-in-effect */
import { useCallback, useEffect, useState } from "react";
import axios from "axios";
import "./App.css";

const API_BASE_URL = "http://localhost:30088/api";

function App() {
  const [plants, setPlants] = useState([]);
  const [form, setForm] = useState({
    name: "",
    wateringIntervalDays: 7,
    lastWateredDate: "",
    note: "",
  });

  const fetchPlants = useCallback(async () => {
  try {
    const response = await axios.get(`${API_BASE_URL}/plants`);
    setPlants(response.data);
    } catch (error) {
      console.error(error);
      alert("식물 목록을 불러오지 못했습니다.");
    }
  }, []);

  const createPlant = async (event) => {
    event.preventDefault();

    if (!form.name || !form.lastWateredDate || !form.wateringIntervalDays) {
      alert("식물 이름, 물주기 주기, 마지막 물 준 날짜는 필수입니다.");
      return;
    }

    try {
      await axios.post(`${API_BASE_URL}/plants`, {
        name: form.name,
        wateringIntervalDays: Number(form.wateringIntervalDays),
        lastWateredDate: form.lastWateredDate,
        note: form.note,
      });

      setForm({
        name: "",
        wateringIntervalDays: 7,
        lastWateredDate: "",
        note: "",
      });

      fetchPlants();
    } catch (error) {
      console.error(error);
      alert("식물 등록에 실패했습니다.");
    }
  };

  const waterPlant = async (id) => {
    try {
      await axios.patch(`${API_BASE_URL}/plants/${id}/water`);
      fetchPlants();
    } catch (error) {
      console.error(error);
      alert("물주기 처리에 실패했습니다.");
    }
  };

  const deletePlant = async (id) => {
    if (!confirm("정말 삭제할까요?")) {
      return;
    }

    try {
      await axios.delete(`${API_BASE_URL}/plants/${id}`);
      fetchPlants();
    } catch (error) {
      console.error(error);
      alert("삭제에 실패했습니다.");
    }
  };

  useEffect(() => {
    fetchPlants();
  }, [fetchPlants]);

  return (
    <main className="container">
      <h1>Plant Watering Tracker</h1>
      <p className="description">
        식물의 마지막 물 준 날짜를 기준으로 다음 물 주는 날짜를 계산합니다.
      </p>

      <section className="card">
        <h2>식물 등록</h2>

        <form onSubmit={createPlant} className="form">
          <label>
            식물 이름
            <input
              type="text"
              value={form.name}
              onChange={(event) =>
                setForm({ ...form, name: event.target.value })
              }
              placeholder="몬스테라"
            />
          </label>

          <label>
            물주기 주기/일
            <input
              type="number"
              min="1"
              value={form.wateringIntervalDays}
              onChange={(event) =>
                setForm({
                  ...form,
                  wateringIntervalDays: event.target.value,
                })
              }
            />
          </label>

          <label>
            마지막 물 준 날짜
            <input
              type="date"
              value={form.lastWateredDate}
              onChange={(event) =>
                setForm({ ...form, lastWateredDate: event.target.value })
              }
            />
          </label>

          <label>
            메모
            <input
              type="text"
              value={form.note}
              onChange={(event) =>
                setForm({ ...form, note: event.target.value })
              }
              placeholder="거실 창가"
            />
          </label>

          <button type="submit">등록</button>
        </form>
      </section>

      <section className="card">
        <h2>식물 목록</h2>

        {plants.length === 0 ? (
          <p>등록된 식물이 없습니다.</p>
        ) : (
          <table>
            <thead>
              <tr>
                <th>이름</th>
                <th>주기</th>
                <th>마지막 물 준 날짜</th>
                <th>다음 물 주는 날짜</th>
                <th>상태</th>
                <th>메모</th>
                <th>관리</th>
              </tr>
            </thead>
            <tbody>
              {plants.map((plant) => (
                <tr key={plant.id}>
                  <td>{plant.name}</td>
                  <td>{plant.wateringIntervalDays}일</td>
                  <td>{plant.lastWateredDate}</td>
                  <td>{plant.nextWateringDate}</td>
                  <td>
                    <span className={`status ${plant.status.toLowerCase()}`}>
                      {plant.status}
                    </span>
                  </td>
                  <td>{plant.note}</td>
                  <td className="actions">
                    <button onClick={() => waterPlant(plant.id)}>
                      물 줬어요
                    </button>
                    <button
                      className="danger"
                      onClick={() => deletePlant(plant.id)}
                    >
                      삭제
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </section>
    </main>
  );
}

export default App;