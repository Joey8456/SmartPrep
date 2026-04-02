import { useState } from "react";
import Login from "./Login";
import "./App.css";
import Questionnaire from "./Questionnaire";
import { INTAKE_QUESTIONS } from "./Questions";
import Dashboard from "./Dashboard";
import ProblemPage from "./ProblemPage";

function App() {
  const [screen, setScreen] = useState("login");
  const [profile, setProfile] = useState({});
  const [intake, setIntake] = useState(null);

  return (
    <>
      {screen === "login" && (
        <Login
          goToQuestionnaire={(profileData) => {
            setProfile(profileData);
            setScreen("questionnaire");
          }}
        />
      )}

      {screen === "questionnaire" && (
        <Questionnaire
          questions={INTAKE_QUESTIONS}
          goBack={() => setScreen("login")}
          onSubmit={(payload) => {
            const fullPayload = {
              ...payload,
              profile
            };
            setIntake(fullPayload);
            setScreen("dashboard");
          }}
        />
      )}

      {screen === "dashboard" && (
        <Dashboard
          intake={intake}
          goBackToQuestionnaire={() => setScreen("questionnaire")}
          goToProblemPage={() => setScreen("ProblemPage")}
        />
      )}

      {screen === "ProblemPage" && (
        <ProblemPage goBack={() => setScreen("dashboard")} />
      )}
    </>
  );
}

export default App;