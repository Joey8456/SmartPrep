import "./App.css";
import { useState } from "react";

export default function Onboarding({ goToQuestionnaire, goBackToLogin }) {
  const [name, setName] = useState("");
  const [level, setLevel] = useState("beginner");

  function handleSubmit(e) {
    e.preventDefault();

    // Save for later screens (simple + beginner-friendly)
    localStorage.setItem("smartprep_name", name.trim());
    localStorage.setItem("smartprep_level", level);

    goToQuestionnaire();
  }

  return (
    <main className="container">
      <header className="header">
        <h1>SmartPrep</h1>
        <p className="subtitle">Quick setup so we can personalize your practice</p>
      </header>

      <form className="form" onSubmit={handleSubmit}>
        <label htmlFor="name">Display name (optional)</label>
        <input
          id="name"
          type="text"
          placeholder="e.g., Ibrahim"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />

        <label>Java level</label>

        <div className="levelGrid">
          <button
            type="button"
            className={"levelBtn " + (level === "beginner" ? "selected" : "")}
            onClick={() => setLevel("beginner")}
          >
            Beginner
          </button>

          <button
            type="button"
            className={"levelBtn " + (level === "intermediate" ? "selected" : "")}
            onClick={() => setLevel("intermediate")}
          >
            Intermediate
          </button>

          <button
            type="button"
            className={"levelBtn " + (level === "advanced" ? "selected" : "")}
            onClick={() => setLevel("advanced")}
          >
            Advanced
          </button>
        </div>

        <button className="primary" type="submit">
          Start Questionnaire
        </button>

        <button className="secondary" type="button" onClick={goBackToLogin}>
          Back to Login
        </button>
      </form>
    </main>
  );
}