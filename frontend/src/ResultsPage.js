import "./ResultsPage.css";

export default function ResultsPage({ result, goBack }) {
  const grade = result.grade;
  const passed = result.passed;
  const total = result.total;
  const runTimeMs = result.runTimeMs;
  const runtimeLogic = result.runTimeMessage;
  const score = result.score

  const failedCases = result.failedCases;
  const message = result?.message || "Submission saved. Your progress has been updated.";
  const gradeKey = String(grade).toLowerCase();

  const gradeStyles = {
    green: {
      label: "GREEN",
      className: "green"
    },
    yellow: {
      label: "YELLOW",
      className: "yellow"
    },
    red: {
      label: "RED",
      className: "red"
    }
  };

  const style = gradeStyles[gradeKey] || gradeStyles.green;

  return (
    <main className="results-shell">
      <section className={`results-card ${style.className}`}>
        <div className="results-header">
          <p className="results-subtitle">Submission Results</p>
          <h1>
            Grade: <span className="grade-text">{style.label}</span>
          </h1>
          <p className="results-message">{message}</p>
        </div>

        <div className="results-grid">
          <div className="result-stat-card">
            <span>Tests Passed</span>
            <strong>{passed} / {total}</strong>
          </div>

          <div className="result-stat-card">
            <span>Score</span>
            <strong>{score}</strong>
          </div>

          <div className="results-feedback runtime-block">
            <h3>Runtime</h3>
            <p className="runtime-main">{runTimeMs} ms</p>
            <p className="runtime-logic-text">
              {runtimeLogic === "Good" ? "Runs Good!" : "Runs Poorly"}
            </p>
          </div>
        </div>

        <div className="results-feedback">
          <h3>Feedback</h3>
          {failedCases.length === 0 ? (
            <p>No failed test cases. Clean submission.</p>
          ) : (
            <div>
              {failedCases.map((tc, i) => (
                <div key={tc.testId || i}>
                  Test Case {tc.testId || i + 1} failed
                </div>
              ))}
            </div>
          )}
        </div>

        <button className="results-btn" onClick={goBack}>
          Back to Problems
        </button>
      </section>
    </main>
  );
}