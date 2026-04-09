import { useState } from "react";
import Login from "./Login";
import "./App.css";
import Questionnaire from "./Questionnaire";
import { INTAKE_QUESTIONS } from "./Questions";
import Dashboard from "./Dashboard";
import ProblemPage from "./ProblemPage";
import ProblemSelection from "./ProblemSelection";
import { UserProvider } from "./UserContext";


function App() {
  const [screen, setScreen] = useState("login");
  const [intake, setIntake] = useState(null);
  const [selectedTopic, setSelectedTopic] = useState("");
  const [selectedProblem, setSelectedProblem] = useState(null);

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
          goToProblemPage={(problemDto, topic) => {
            setSelectedProblem(problemDto);
            setSelectedTopic(topic || "");
            setScreen("ProblemPage");
          }}
        />
      )}

      {screen === "ProblemPage" && (
        <ProblemPage
          topic={selectedTopic}
          problem={selectedProblem}
          goBack={() => setScreen("problemSelection")}
        />
      )}
    </UserProvider>
  );
}

export default App;