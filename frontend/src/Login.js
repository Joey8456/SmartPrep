import "./App.css";
import "./Login.css";
import { useState } from "react";

export default function Login({ goToQuestionnaire }) {
  const [displayName, setDisplayName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [error, setError] = useState("");

  function handleSubmit(e) {
    e.preventDefault();
    setError("");

    if (!email || !password || !confirmPassword) {
      setError("Please enter email, password, and confirm password.");
      return;
    }

    if (!email.includes("@")) {
      setError("Enter a valid email.");
      return;
    }

    if (password.length < 6) {
      setError("Password must be at least 6 characters.");
      return;
    }

    if (password !== confirmPassword) {
      setError("Passwords do not match.");
      return;
    }

    goToQuestionnaire({
      display_name: displayName,
      email
    });
  }

  return (
    <main className="container">
      <div className="card loginCard">
        <h1>SmartPrep</h1>
        <p className="subtitle">Sign in to continue</p>

        <form onSubmit={handleSubmit} className="form">
          <label>Display name (optional)</label>
          <input
            type="text"
            placeholder="e.g., Ibrahim"
            value={displayName}
            onChange={(e) => setDisplayName(e.target.value)}
          />

          <label>Email</label>
          <input
            type="email"
            placeholder="you@example.com"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />

          <label>Password</label>
          <input
            type="password"
            placeholder="••••••••"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />

          <label>Confirm Password</label>
          <input
            type="password"
            placeholder="••••••••"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
          />

          {error && <p className="errorText">{error}</p>}

          <button className="primary" type="submit">
            Start Questionnaire
          </button>
        </form>

        <div className="divider">or</div>

        <button className="secondary" type="button">
          Continue with Google
        </button>
        <button className="secondary" type="button">
          Continue with Apple
        </button>

        <p className="footerText">New here? Create an account</p>
      </div>
    </main>
  );
}