import "./App.css";
import { useState } from "react";
import { useUser } from "./UserContext";

export default function Login({ goToQuestionnaire }) {
  const [email, setEmail] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [error, setError] = useState("");
  const [isLogin, setIsLogin] = useState(false);
  const { setUser } = useUser();

  async function handleSubmit(e) {
    e.preventDefault();
    setError("");
    if (!email || !password || (!isLogin && (!username || !confirmPassword))) {
      setError("Please fill in all required fields.");
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
            "Content-Type": "application/json",
          },
          body: JSON.stringify(
            isLogin
              ? {
                  email: email,
                  passhash: password,
                }
              : {
                  username: username,
                  email: email,
                  passhash: password,
                }
          ),
        }
      );

      if (!res.ok) {
        throw new Error("Failed to create user");
      }

      const createdUser = await res.json();

      console.log(createdUser);
      console.log(createdUser.userId);
      setUser(createdUser);
      goToQuestionnaire();
      return;
    } catch (err) {
      console.error(err);
      setError(err.message || "Failed to connect to server");
    }
  }

  return (
    <main className="container">
      <header className="header">
        <h1>SmartPrep</h1>
        <p className="subtitle">
          {isLogin ? "Sign in to continue" : "Create your account to continue"}
        </p>
      </header>

      <form className="form" onSubmit={handleSubmit}>
        {!isLogin && (
          <>
            <label htmlFor="username">Username</label>
            <input
              id="username"
              type="text"
              placeholder="your username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
            />
          </>
        )}

        <label htmlFor="email">Email</label>
        <input
          id="email"
          type="email"
          placeholder="you@example.com"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />

        <label htmlFor="password">Password</label>
        <input
          id="password"
          type="password"
          placeholder="••••••••"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        {!isLogin && (
          <>
            <label htmlFor="confirmPassword">Confirm Password</label>
            <input
              id="confirmPassword"
              type="password"
              placeholder="••••••••"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
            />
          </>
        )}

        <button className="primary" type="submit">
          {isLogin ? "Sign In" : "Sign Up"}
        </button>
        <p className="error">{error}</p>
      </form>
      <p style={{ marginTop: "1rem", textAlign: "center" }}>
        {isLogin ? "Don't have an account?" : "Already have an account?"}
        <button
          type="button"
          className="secondary"
          onClick={() => {
            setIsLogin(!isLogin);
            setError("");
          }}
          style={{ marginLeft: "8px" }}
        >
          {isLogin ? "Sign Up" : "Sign In"}
        </button>
      </p>
    </main>
  );
}