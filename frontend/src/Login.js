import "./App.css";
import { useState } from "react";

export default function Login({ goToOnboarding }) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  async function handleSubmit(e) {
    e.preventDefault();
    setError("");

    if (!email || !password) {
      setError("Please enter email and password.");
      return;
    }

  try {
    const res = await fetch("http://localhost:8080/api/v1/users", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        username: email,   // temp
        email: email,
        passhash: password,
      }),
    });

    if (!res.ok) {
      const text = await res.text();
      throw new Error(text);
    }

    console.log("User created");
    goToOnboarding();

  } catch (err) {
    console.error(err);
    setError("Failed to connect to server");
  }

    goToOnboarding();
  }

  return (
    <main className="container">
      <header className="header">
        <h1>SmartPrep</h1>
        <p className="subtitle">Sign up to continue</p>
      </header>

      <form className="form" onSubmit={handleSubmit}>
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
