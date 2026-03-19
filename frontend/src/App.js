import { useState } from "react";
import Login from "./Login";
import Onboarding from "./Onboarding";
import "./App.css";
import Questionnaire from "./Questionnaire";
import { INTAKE_QUESTIONS } from "./Questions";
import Dashboard from "./Dashboard";

function App() {
  const [screen, setScreen] = useState("login");
  const [intake, setIntake] = useState(null);

  return (
    <>
      {screen === "login" && (
        <Login goToOnboarding={() => setScreen("onboarding")} />
      )}

      {screen === "onboarding" && (
        <Onboarding
          goToQuestionnaire={() => setScreen("questionnaire")}
          goBackToLogin={() => setScreen("login")}
        />
      )}

      {screen === "questionnaire" && (
        <Questionnaire
          questions={INTAKE_QUESTIONS}
          goBack={() => setScreen("onboarding")}
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
        />
      )}
    </>
  );
}

export default App;