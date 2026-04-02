import "./App.css";
import "./Login.css";
import { useState } from "react";
import { useUser } from "./UserContext";

export default function Login({ goToQuestionnaire }) {
  const [displayName, setDisplayName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [error, setError] = useState("");
  const [isLogin, setIsLogin] = useState(false);
  const { setUser } = useUser();

  async function handleSubmit(e) {
    e.preventDefault();
    setError("");

    if (!email || !password || (!isLogin && (!displayName || !confirmPassword))) {
      setError("Please fill in all required fields.");
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

    if (!isLogin && password !== confirmPassword) {
      setError("Passwords do not match.");
      return;
    }

    try {
      const res = await fetch(
        isLogin
          ? "http://localhost:8080/api/v1/users/login"
          : "http://localhost:8080/api/v1/users",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify(
            isLogin
              ? {
                  email: email,
                  passhash: password
                }
              : {
                  username: displayName,
                  email: email,
                  passhash: password
                }
          )
        }
      );

      if (!res.ok) {
        throw new Error(isLogin ? "Failed to sign in." : "Failed to create account.");
      }

      const createdUser = await res.json();
      setUser(createdUser);
      goToQuestionnaire();
    } catch (err) {
      console.error(err);
      setError(err.message || "Failed to connect to server.");
    }
  }

  return (
    <main className="container">
      <div className="card loginCard">
        <h1>SmartPrep</h1>
        <p className="subtitle">
          {isLogin ? "Sign in to continue" : "Create your account to continue"}
        </p>

        <form onSubmit={handleSubmit} className="form">
          {!isLogin && (
            <>
              <label>Display name</label>
              <input
                type="text"
                placeholder="e.g., Ibrahim"
                value={displayName}
                onChange={(e) => setDisplayName(e.target.value)}
              />
            </>
          )}

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

          {!isLogin && (
            <>
              <label>Confirm Password</label>
              <input
                type="password"
                placeholder="••••••••"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
              />
            </>
          )}

          {error && <p className="errorText">{error}</p>}

          <button className="primary" type="submit">
            {isLogin ? "Sign In" : "Start Questionnaire"}
          </button>
        </form>

        <div className="divider">or</div>

        <button className="secondary" type="button">
          Continue with Google
        </button>
        <button className="secondary" type="button">
          Continue with Apple
        </button>

        <p className="footerText">
          {isLogin ? "Don't have an account?" : "Already have an account?"}
        </p>

        <button
          type="button"
          className="secondary"
          onClick={() => {
            setIsLogin(!isLogin);
            setError("");
          }}
        >
          {isLogin ? "Create an account" : "Sign in instead"}
        </button>
      </div>
    </main>
  );
}