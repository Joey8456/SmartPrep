import { useEffect, useState } from "react";
import "./App.css";
import "./ProblemPage.css";
import Editor from "@monaco-editor/react";

export default function ProblemPage({ topic, problem, goBack, userId }) {
  const [error, setError] = useState("");
  const problemData = {
    "Arrays & Strings": {
      title: "Two Sum",
      topicLabel: "Arrays & Strings",
      difficulty: "Easy",
      explanation:
        "Given an array of integers nums and an integer target, return the indices of the two numbers such that they add up to target.",
      exampleInput: "nums = [2, 7, 11, 15], target = 9",
      exampleOutput: "[0, 1]",
      testCases: [
        "[2,7,11,15], 9 → [0,1]",
        "[3,2,4], 6 → [1,2]",
        "[3,3], 6 → [0,1]"
      ],
      starterCode: `class Solution {
    public int[] twoSum(int[] nums, int target) {

    }
}`
    },

    "Two Pointers": {
      title: "Valid Palindrome",
      topicLabel: "Two Pointers",
      difficulty: "Easy",
      explanation:
        "Given a string s, return true if it is a palindrome after converting all uppercase letters into lowercase letters and removing all non-alphanumeric characters.",
      exampleInput: `s = "A man, a plan, a canal: Panama"`,
      exampleOutput: "true",
      testCases: [
        `"A man, a plan, a canal: Panama" → true`,
        `"race a car" → false`,
        `" " → true`
      ],
      starterCode: `class Solution {
    public boolean isPalindrome(String s) {

    }
}`
    },

    "Hash Maps": {
      title: "Contains Duplicate",
      topicLabel: "Hash Maps",
      difficulty: "Easy",
      explanation:
        "Given an integer array nums, return true if any value appears at least twice in the array, and return false if every element is distinct.",
      exampleInput: "nums = [1, 2, 3, 1]",
      exampleOutput: "true",
      testCases: [
        "[1,2,3,1] → true",
        "[1,2,3,4] → false",
        "[1,1,1,3,3,4,3,2,4,2] → true"
      ],
      starterCode: `class Solution {
    public boolean containsDuplicate(int[] nums) {

    }
}`
    }
  };

  const fallbackProblem =
    problemData[topic || "Arrays & Strings"] || problemData["Arrays & Strings"];

  const currentProblem = problem
    ? {
        title: problem.title || "Problem Title",
        topicLabel: topic ?? `Category ${problem.category || ""}`,
        difficulty: problem.problemDifficulty || "UNKNOWN",
        explanation: problem.prompt || "Problem prompt unavailable.",
        exampleInput: problem.examples || "No examples available.",
        exampleOutput: "",
        starterCode: problem.starterCode || `class Solution {

}`
      }
    : fallbackProblem;

  const sampleTestCasesText =
    problem && typeof problem.sampleTestCase === "string" && problem.sampleTestCase.trim().length > 0
      ? problem.sampleTestCase
      : "No test cases available.";

  const resolvedUserId = userId;
  const resolvedCategoryId = problem?.category ?? null;

  const [code, setCode] = useState(currentProblem.starterCode);
  const [output, setOutput] = useState("Output will appear here when submitted.");
  const [resultStatus, setResultStatus] = useState(null); // "red" | "yellow" | "green" | null

  useEffect(() => {
    setCode(currentProblem.starterCode);
    setOutput("Output will appear here when submitted.");
    setResultStatus(null);
  }, [topic, problem, currentProblem.starterCode]);

  async function handleSubmit() {
    const trimmedCode = code.trim();
    console.log("problem:", problem);
    console.log("problem.problemId:", problem?.problemId);
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

      const data = await res.text();

      if (!res.ok) {
        throw new Error(data || "Failed to submit solution");
      }

      console.log(data);
      setOutput(data);
    } catch (err) {
      console.error(err);
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
              <h3>Example</h3>
              <p>
                <strong>Example:</strong> {currentProblem.exampleInput}
              </p>
              {currentProblem.exampleOutput ? (
                <p>
                  <strong>Output:</strong> {currentProblem.exampleOutput}
                </p>
              ) : null}
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
              <button className="primary" type="button" onClick={handleSubmit}>
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
        </section>
      </div>
    </main>
  );
}