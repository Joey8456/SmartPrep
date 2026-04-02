import "./App.css";
import "./ProblemPage.css";

export default function ProblemPage({ goBack }) {
  return (
    <main className="problem-page-shell">
      <div className="problem-top-nav">
        <div className="problem-brand">SmartPrep</div>

        <div className="problem-nav-tabs">
          <button className="problem-tab active" type="button">
            Problem
          </button>
          <button className="problem-tab" type="button">
            Help
          </button>
          <button className="problem-tab" type="button">
            Code
          </button>
        </div>
      </div>

      <div className="problem-layout">
        <section className="problem-panel left-panel">
          <div className="problem-title-row">
            <h2>Two Sum</h2>
            <span className="difficulty-badge easy">Easy</span>
          </div>

          <div className="topic-pill">Arrays & Strings</div>

          <div className="problem-section">
            <h3>Problem Description</h3>
            <p>
              Given an array of integers <strong>nums</strong> and an integer{" "}
              <strong>target</strong>, return indices of the two numbers such
              that they add up to target.
            </p>
          </div>

          <div className="problem-section">
            <h3>Example</h3>
            <p>
              <strong>Input:</strong> nums = [2, 7, 11, 15], target = 9
            </p>
            <p>
              <strong>Output:</strong> [0, 1]
            </p>
          </div>

          <div className="constraint-box">
            <strong>Constraints:</strong> 2 ≤ nums.length ≤ 10⁴ • Only one valid answer exists
          </div>
        </section>

        <section className="problem-panel right-panel">
          <div className="editor-topbar">
            <span className="editor-file">solution.java</span>

            <div className="editor-dots">
              <span className="dot red"></span>
              <span className="dot yellow"></span>
              <span className="dot green"></span>
            </div>
          </div>

          <div className="code-area">
            <pre>{`class Solution {
    public int[] twoSum(int[] nums, int target) {
        // Write your solution here

    }
}`}</pre>
          </div>

          <div className="editor-actions">
            <button className="run-btn" type="button">Run</button>
            <button className="submit-btn" type="button">Submit</button>
            <button className="hint-btn" type="button">Hint</button>

            <button
              className="back-dashboard-btn"
              type="button"
              onClick={goBack}
            >
              Back
            </button>
          </div>
        </section>
      </div>
    </main>
  );
}