import { useState } from "react";
import Login from "./Login";
import "./App.css";
import Questionnaire from "./Questionnaire";
import { INTAKE_QUESTIONS } from "./Questions";
import ProblemPage from "./ProblemPage";
import ProblemSelection from "./ProblemSelection";
import ChatbotPage from "./ChatbotPage";
import { UserProvider } from "./UserContext";

function App() {
  const [screen, setScreen] = useState("login");
  const [selectedTopic, setSelectedTopic] = useState("");
  const [selectedProblem, setSelectedProblem] = useState(null);

  return (
      <UserProvider>
        {screen === "login" && (
            <Login
                goToQuestionnaire={() => {
                  setScreen("questionnaire");
                }}
                goToProblemSelection={() => {
               setScreen("problemSelection");
                }}
            />
        )}

        {screen === "questionnaire" && (
            <Questionnaire
                questions={INTAKE_QUESTIONS}
                goBack={() => setScreen("login")}
                onSubmit={(payload) => {
                  setScreen("problemSelection");
                }}
            />
        )}

        {screen === "problemSelection" && (
                <ProblemSelection
                  goBack={() => setScreen("questionnaire")}
                  goToProblemPage={(problemDto, topic) => {
                    setSelectedProblem(problemDto);
                    setSelectedTopic(topic || "");
                    setScreen("ProblemPage");
                  }}
                  goToChatbot={() => setScreen("chatbot")}
                />
              )}

       {screen === "ProblemPage" && (
               <ProblemPage
                 topic={selectedTopic}
                 problem={selectedProblem}
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