import "./App.css";
import "./ProblemSelection.css";
import { useUser } from "./UserContext";

export default function ProblemSelection({ goToProblemPage, goBack, goToChatbot }) {
  const { user } = useUser();

  const topics = [
    { key: "arrays", label: "Arrays & Strings", progress: 25 },
    { key: "twoPointers", label: "Two Pointers", progress: 40 },
    { key: "hashMaps", label: "Hash Maps", progress: 15 }
  ];

  return (
      <main className="selection-shell">
        <div className="selection-card">
          <div className="selection-topbar">
            <h1>SmartPrep</h1>
            <div className="selection-user">
              @{user?.username || "username"}
            </div>
          </div>

          <div className="selection-layout">
            <section className="selection-left">
              {topics.map((topic) => (
                  <button
                      key={topic.key}
                      type="button"
                      className="topic-card"
                      onClick={() => goToProblemPage(topic.label)}
                  >
                    <div className="topic-title">{topic.label}</div>

                    <div className="topic-progress-row">
                      <span className="topic-progress-label">{topic.progress}%</span>
                      <div className="topic-progress-track">
                        <div
                            className="topic-progress-fill"
                            style={{ width: `${topic.progress}%` }}
                        ></div>
                      </div>
                    </div>
                  </button>
              ))}
            </section>

            <section className="selection-right">
              <button
                  type="button"
                  className="side-action-btn primary-side-btn"
                  onClick={() => goToProblemPage("Random Problem")}
              >
                Random Problem
              </button>

              <button
                  type="button"
                  className="side-action-btn secondary-side-btn"
                  onClick={goToChatbot}
              >
                AI Chatbot
              </button>

              <button
                  type="button"
                  className="side-action-btn secondary-side-btn"
                  onClick={goBack}
              >
                Back
              </button>
            </section>
          </div>
        </div>
      </main>
  );
}