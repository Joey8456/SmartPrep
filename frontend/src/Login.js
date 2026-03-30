import "./App.css";
import { useState } from "react";
import { useUser } from "./UserContext";

export default function Login({ goToQuestionnaire }) {
  const [email, setEmail] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const { setUser } = useUser();

  async function handleSubmit(e) {
    e.preventDefault();
    setError("");

    if (!username || !email || !password) {
      setError("Please enter username, email, and password.");
      return;
    }

    try {
      const res = await fetch("http://localhost:8080/api/v1/users", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          username: username,
          email: email,
          passhash: password,
        }),
      });

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
      setError("Failed to connect to server");
    }
  }

  return (
    <main className="container">
      <header className="header">
        <h1>SmartPrep</h1>
        <p className="subtitle">Create your account to continue</p>
      </header>

      <form className="form" onSubmit={handleSubmit}>
        <label htmlFor="username">Username</label>
        <input
          id="username"
          type="text"
          placeholder="your username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />

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

        <button className="primary" type="submit">Sign Up</button>
        <p className="error">{error}</p>
      </form>

      <div className="divider"><span>or</span></div>

      <button className="secondary" type="button">Continue with Google</button>
      <button className="secondary" type="button">Continue with Apple</button>
    </main>
  );
}