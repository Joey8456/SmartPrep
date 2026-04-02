import "./App.css";
import "./Questionnaire.css";
import { useMemo, useState } from "react";

function setNestedValue(obj, path, value) {
  const parts = path.split(".");
  let curr = obj;
  for (let i = 0; i < parts.length - 1; i++) {
    const key = parts[i];
    if (!curr[key] || typeof curr[key] !== "object") curr[key] = {};
    curr = curr[key];
  }
  curr[parts[parts.length - 1]] = value;
}

export default function Questionnaire({ questions, goBack, onSubmit }) {
  const [index, setIndex] = useState(0);
  const [responses, setResponses] = useState({});

  const q = questions[index];

  const progressText = useMemo(
    () => `Question ${index + 1} of ${questions.length}`,
    [index, questions.length]
  );

  const progressPct = useMemo(() => {
    if (!questions?.length) return 0;
    return Math.round(((index + 1) / questions.length) * 100);
  }, [index, questions.length]);

  function updateResponse(questionId, value) {
    setResponses((prev) => {
      const next = structuredClone(prev);
      if (questionId.includes(".")) {
        setNestedValue(next, questionId, value);
      } else {
        next[questionId] = value;
      }
      return next;
    });
  }

  function next() {
    if (value === undefined) {
      alert("Please select an answer before continuing.");
      return;
    }

    if (index < questions.length - 1) {
      setIndex(index + 1);
    }
  }

  function prev() {
    if (index > 0) setIndex(index - 1);
  }

  function submit() {
    const payload = {
      schema_version: "smartprep_intake_v1",
      submitted_at: new Date().toISOString(),
      user_id: "temp-id",
      responses
    };

    console.log("INTAKE PAYLOAD:", JSON.stringify(payload, null, 2));

    if (onSubmit) {
      onSubmit(payload);
    }
  }

  function getValue() {
    if (!q.id.includes(".")) return responses[q.id];
    const parts = q.id.split(".");
    let curr = responses;
    for (const p of parts) {
      if (!curr) return undefined;
      curr = curr[p];
    }
    return curr;
  }

  const value = getValue();

  return (
    <main className="container">
      <header className="header">
        <h1>SmartPrep</h1>
        <p className="subtitle">{progressText}</p>

        <div className="progressWrap">
          <div className="progressTrack">
            <div
              className="progressFill"
              style={{ width: `${progressPct}%` }}
            />
          </div>
          <span className="progressLabel">{progressPct}%</span>
        </div>
      </header>

      <div className="card">
        <p className="prompt">{q.prompt}</p>

        {q.type === "single_select" && (
          <div className="choices">
            {q.options.map((opt) => (
              <button
                key={opt}
                type="button"
                className={"choiceBtn " + (value === opt ? "selected" : "")}
                onClick={() => updateResponse(q.id, opt)}
              >
                {opt}
              </button>
            ))}
          </div>
        )}

        {q.type === "rating_1_5" && (
          <div className="ratingRow">
            {[1, 2, 3, 4, 5].map((n) => (
              <button
                key={n}
                type="button"
                className={"ratingBtn " + (value === n ? "selected" : "")}
                onClick={() => updateResponse(q.id, n)}
              >
                {n}
              </button>
            ))}
          </div>
        )}
      </div>

      <div className="row">
        <button className="secondary" type="button" onClick={goBack}>
          Back
        </button>

        <button
          className="secondary"
          type="button"
          onClick={prev}
          disabled={index === 0}
        >
          Previous
        </button>

        {index < questions.length - 1 ? (
          <button className="primary" type="button" onClick={next}>
            Next
          </button>
        ) : (
          <button className="primary" type="button" onClick={submit}>
            Submit
          </button>
        )}
      </div>
    </main>
  );
}