import "./App.css";
import { useState } from "react";

export default function Login({ goToOnboarding }) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  function handleSubmit(e) {
    e.preventDefault();
    setError("");

    if (!email || !password) {
      setError("Please enter email and password.");
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

    goToOnboarding();
  }

  return (
    <main className="container">
      <header className="header">
        <h1>SmartPrep</h1>
        <p className="subtitle">Sign in to continue</p>
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

        <button className="primary" type="submit">Log In</button>
        <p className="error">{error}</p>
      </form>

      <div className="divider"><span>or</span></div>

      <button className="secondary" type="button">Continue with Google</button>
      <button className="secondary" type="button">Continue with Apple</button>

      <p className="footer">New here? Create an account</p>
    </main>
  );
}
