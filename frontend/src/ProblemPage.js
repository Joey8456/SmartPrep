import { useEffect, useState } from "react";
import "./App.css";
import "./ProblemPage.css";
import Editor from "@monaco-editor/react";

export default function ProblemPage({ topic, problem, goBack, userId, onSubmit }) {
  const [error, setError] = useState("");
  const currentProblem = {
    title: problem.title,
    topicLabel: topic,
    difficulty: problem.problemDifficulty || problem.difficulty,
    explanation: problem.prompt || problem.description,
    exampleInput: problem.examples,
    exampleOutput: problem.sampleExpectedOutput,
    starterCode: problem.starterCode
  };

  const sampleTestCasesText = problem.sampleTestCase;
  const resolvedUserId = userId;
  const resolvedCategoryId = problem.category;
  const [code, setCode] = useState(currentProblem.starterCode);
  const [output, setOutput] = useState("Output will appear here when submitted.");
  const [resultStatus, setResultStatus] = useState(null); // "red" | "yellow" | "green" | null

  useEffect(() => {
    setCode(currentProblem.starterCode);
    setOutput("Output will appear here when submitted.");
    setResultStatus(null);
  }, [topic, problem, currentProblem.starterCode]);

  async function handleRun() {
    const trimmedCode = code.trim();

    try {
      const res = await fetch("http://localhost:8080/api/v1/solution/run", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          codeString: trimmedCode,
          problemId: problem.problemId,
          userId: resolvedUserId,
          categoryId: resolvedCategoryId,
        }),
      });

      const data = await res.text();

      if (!res.ok) {
        throw new Error(data || "Failed to run solution");
      }

      setOutput(data);
    } catch (err) {
      const message = err.message || "Run failed";
      setError(message);
      setOutput(message);
    }
  }

  async function handleSubmit() {
    const trimmedCode = code.trim();

    try {
      const res = await fetch("http://localhost:8080/api/v1/solution", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          codeString: trimmedCode,
          problemId: problem.problemId,
          userId: resolvedUserId,
          categoryId: resolvedCategoryId,
        }),
      });

      const data = await res.json();

      if (!res.ok) {
        throw new Error(data?.message || "Failed to submit solution");
      }

      const passed = data.passed;
      const total = data.total;
      const grade = data.color;
      const scoreValue = data.score;
      const score = typeof scoreValue === "number"
        ? `${Math.round(scoreValue * 100)}%`
        : scoreValue;
      const runTimeMs = data.runTimeMs;
      const runTimeMessage = data.runTimeMessage;

      const formattedResults = {
        grade,
        passed,
        total,
        score,
        runTimeMs,
        runTimeMessage,
        failedCases: data.failedCases,
        error: data.error,
        message: data.message ?? "Submission saved. Your progress has been updated."
      };

      setOutput(`Finished grading. Passed ${passed} / ${total} test cases.\nColor Grade: ${grade}`);

      if (onSubmit) {
        onSubmit(formattedResults);
      }
    } catch (err) {
      const message = err.message || "Submit failed";
      setError(message);
      setOutput(message);
    }
  }

  if (!currentProblem) {
    return (
      <main className="workspace-shell">
        <div className="workspace-layout">
          <section className="workspace-left">
            <div className="workspace-card">
              <h2>No problem loaded</h2>
              <p>Please go back and select a problem.</p>
              <button className="secondary" type="button" onClick={goBack}>
                Back
              </button>
            </div>
          </section>
        </div>
      </main>
    );
  }

  return (
    <main className="workspace-shell">
      <div className="workspace-layout">
        <section className="workspace-left">
          <div className="workspace-card">
            <div className="problem-title-row">
              <div>
                <h2>{currentProblem.title}</h2>
                <div className="topic-pill">{currentProblem.topicLabel}</div>
              </div>

              <span className={`difficulty-badge ${String(currentProblem.difficulty || "").toLowerCase()}`}>
                {currentProblem.difficulty}
              </span>
            </div>

            <div className="workspace-section">
              <h3>Problem Explanation</h3>
              <p>{currentProblem.explanation}</p>
            </div>

            <div className="workspace-section">
              <h3>Run Test Case</h3>
              <p style={{ fontSize: "0.9rem", color: "#9fb0ff" }}>
                This is the test case used when you click <strong>Run</strong>.
                Use it to debug your solution before submitting.
              </p>

              <div className="example-box">
                <p>
                  <strong>Input:</strong> {currentProblem.exampleInput}
                </p>

                {currentProblem.exampleOutput ? (
                  <p>
                    <strong>Expected Output:</strong> {currentProblem.exampleOutput}
                  </p>
                ) : null}
              </div>
            </div>

            <div className="workspace-section">
              <h3>Sample Test Cases</h3>
              <pre
                className="sample-test-cases-block"
                style={{ whiteSpace: "pre-wrap", margin: 0 }}
              >
                {sampleTestCasesText}
              </pre>
            </div>
          </div>
        </section>

        <section className="workspace-right">
          <div className="editor-card">
            <div className="editor-topbar">
              <span>solution.java</span>

              <div className="editor-status-dots">
                <span className={`status-dot red ${resultStatus === "red" ? "active-red" : ""}`}></span>
                <span className={`status-dot yellow ${resultStatus === "yellow" ? "active-yellow" : ""}`}></span>
                <span className={`status-dot green ${resultStatus === "green" ? "active-green" : ""}`}></span>
              </div>
            </div>

            <div className="editor-wrapper">
              <Editor
                height="420px"
                defaultLanguage="java"
                value={code}
                onChange={(value) => setCode(value || "")}
                theme="vs-dark"
                options={{
                  minimap: { enabled: false },
                  fontSize: 15,
                  scrollBeyondLastLine: false,
                  wordWrap: "on",
                  padding: { top: 16 }
                }}
              />
            </div>

            <div className="editor-actions">
              <button className="primary" type="button" onClick={handleRun}>
                Run
              </button>

              <button className="secondary" type="button" onClick={goBack}>
                Back
              </button>
            </div>
          </div>

          <div className="output-card">
            <h3>Output</h3>
            <p>{output}</p>
          </div>
          <button
            type="button"
            className="submit-solution-btn"
            onClick={handleSubmit}
          >
            🚀 Submit Final Answer
          </button>
        </section>
      </div>
    </main>
  );
}