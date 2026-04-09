import { useState } from "react";
import Login from "./Login";
import "./App.css";
import Questionnaire from "./Questionnaire";
import { INTAKE_QUESTIONS } from "./Questions";
import Dashboard from "./Dashboard";
import ProblemPage from "./ProblemPage";
import ProblemSelection from "./ProblemSelection";
import ChatbotPage from "./ChatbotPage";
import { UserProvider } from "./UserContext";

function App() {
  const [screen, setScreen] = useState("login");
  const [intake, setIntake] = useState(null);
  const [selectedTopic, setSelectedTopic] = useState("");

  return (
      <UserProvider>
        {screen === "login" && (
            <Login
                goToQuestionnaire={() => {
                  setScreen("questionnaire");
                }}
            />
        )}

        {screen === "questionnaire" && (
            <Questionnaire
                questions={INTAKE_QUESTIONS}
                goBack={() => setScreen("login")}
                onSubmit={(payload) => {
                  setIntake(payload);
                  setScreen("dashboard");
                }}
            />
        )}

        {screen === "dashboard" && (
            <Dashboard
                intake={intake}
                goBackToQuestionnaire={() => setScreen("questionnaire")}
                goToProblemPage={() => setScreen("problemSelection")}
            />
        )}

        {screen === "problemSelection" && (
            <ProblemSelection
                goBack={() => setScreen("dashboard")}
                goToChatbot={() => setScreen("chatbot")}
                goToProblemPage={(topic) => {
                  if (topic === "Random Problem") {
                    const topics = ["Arrays & Strings", "Two Pointers", "Hash Maps"];
                    const randomTopic =
                        topics[Math.floor(Math.random() * topics.length)];
                    setSelectedTopic(randomTopic);
                  } else {
                    setSelectedTopic(topic);
                  }
                  setScreen("ProblemPage");
                }}
            />
        )}

        {screen === "ProblemPage" && (
            <ProblemPage
                topic={selectedTopic}
                goBack={() => setScreen("problemSelection")}
            />
        )}

        {screen === "chatbot" && (
            <ChatbotPage goBack={() => setScreen("problemSelection")} />
        )}
      </UserProvider>
  );
}

export default App;