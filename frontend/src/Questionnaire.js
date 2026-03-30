import "./App.css";
import { useMemo, useState } from "react";
import { useUser } from "./UserContext";

export default function Questionnaire({ questions, goBack, onSubmit }) {
  const { user } = useUser();
  const [index, setIndex] = useState(0);
  const [responses, setResponses] = useState({});

  const safeQuestions = questions ?? [];

  const progressText = useMemo(
    () => `Question ${safeQuestions.length === 0 ? 0 : index + 1} of ${safeQuestions.length}`,
    [index, safeQuestions.length]
  );

  const progressPct = useMemo(() => {
    if (safeQuestions.length === 0) return 0;
    return Math.round(((index + 1) / safeQuestions.length) * 100);
  }, [index, safeQuestions.length]);

  if (safeQuestions.length === 0) {
    return null;
  }

  const q = safeQuestions[index];
  const value = responses[q.id];

  function updateResponse(questionId,proficiency) {
    setResponses((prev) => ({
      ...prev,
      [questionId]: proficiency,
    }));
  }

  function next() {
    if (value === undefined) {
      alert("Please select an answer before continuing.");
      return;
    }

    if (index < safeQuestions.length - 1) {
      setIndex((prev) => prev + 1);
    }
  }

  function prev() {
    if (index > 0) {
      setIndex((prev) => prev - 1);
    }
  }

  async function submit() {
    if (value === undefined) {
      alert("Please select an answer before submitting.");
      return;
    }

    if (!user?.userId) {
      alert("User not found.");
      return;
    }

    const payload = {
      schema_version: "smartprep_intake_v1",
      submitted_at: new Date().toISOString(),
      user_id: user.userId,
      responses,
    };

    const profQuestions = safeQuestions.filter((question) => question.categoryId != null);

    try {
      for (const question of profQuestions) {
        const proficiency = responses[question.id];
        console.log(proficiency);

        if (proficiency === undefined) {
          throw new Error(`Missing answer for ${question.id}`);
        }

        const res = await fetch(
          `http://localhost:8080/api/v1/proficiencies/${user.userId}/${question.categoryId}`,
          {
            method: "PUT",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({
              proficiency,
            }),
          }
        );

        if (!res.ok) {
          throw new Error(`Failed to update category ${question.categoryId}`);
        }
      }

      if (onSubmit) {
        onSubmit(payload);
      }
    } catch (error) {
      console.error("Submit failed:", error);
      alert("Failed to save proficiency updates.");
    }
  }

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

        {index < safeQuestions.length - 1 ? (
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