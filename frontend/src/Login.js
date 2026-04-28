import "./App.css";
import "./Login.css";
import { useState } from "react";
import { useUser } from "./UserContext";

export default function Login({ goToQuestionnaire, goToProblemSelection }) {
  const [form, setForm] = useState({
    email: "",
    username: "",
    password: "",
    confirmPassword: ""
  });
  const [error, setError] = useState("");
  const [isLogin, setIsLogin] = useState(false);
  const { setUser } = useUser();

  async function handleSubmit(e) {
    e.preventDefault();
    setError("");

    const { email, username, password, confirmPassword } = form;

    if (!email || !password || (!isLogin && (!username || !confirmPassword))) {
      setError("Please fill in all required fields.");
      return;
    }

    if (!isLogin && password !== confirmPassword) {
      setError("Passwords do not match.");
      return;
    }

    if (isLogin) {
      try {
       const url = "http://localhost:8080/api/v1/users/login";

       const res = await fetch(url, {
         method: "POST",
         headers: {
           "Content-Type": "application/json",
         },
         body: JSON.stringify({
           email: form.email,
           password: form.password
         }),
       });

        if (!res.ok) {
          throw new Error("Invalid email or password");
        }

        const user = await res.json();

        setUser(user);
        goToProblemSelection();
        return;
      } catch (err) {
        console.error(err);
        setError(err.message || "Login failed");
        return;
      }
    } else {
      try {
        const url = "http://localhost:8080/api/v1/users";


        const res = await fetch(url, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            username: form.username,
            email: form.email,
            passhash: form.password
          }),
        });

        if (!res.ok) {
          throw new Error("Failed to create user");
        }

        const createdUser = await res.json();

        setUser(createdUser);
        goToQuestionnaire();
        return;
      } catch (err) {
        console.error(err);
        setError(err.message || "Failed to connect to server");
      }
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
              <label>Username</label>
              <input
                type="text"
                placeholder="Enter Username"
                value={form.username}
                onChange={(e) =>
                  setForm({ ...form, username: e.target.value })
                }
              />
            </>
          )}

          <label>Email</label>
          <input
            type="email"
            placeholder="Enter Email"
            value={form.email}
            onChange={(e) =>
              setForm({ ...form, email: e.target.value })
            }
          />

          <label>Password</label>
          <input
            type="password"
            placeholder="Enter Password"
            value={form.password}
            onChange={(e) =>
              setForm({ ...form, password: e.target.value })
            }
          />

          {!isLogin && (
            <>
              <label>Confirm Password</label>
              <input
                type="password"
                placeholder="Confirm Password"
                value={form.confirmPassword}
                onChange={(e) =>
                  setForm({ ...form, confirmPassword: e.target.value })
                }
              />
            </>
          )}

          {error && <p className="errorText">{error}</p>}

          <button className="primary" type="submit">
            {isLogin ? "Sign In" : "Sign Up"}
          </button>
        </form>

        <div className="authSwitch">
          <p className="footerText">
            {isLogin ? "Don't have an account?" : "Already have an account?"}
          </p>

          <button
            type="button"
            className="secondary authSwitchBtn"
            onClick={() => {
              setIsLogin(!isLogin);
              setError("");
              setForm({
                email: "",
                username: "",
                password: "",
                confirmPassword: ""
              });
            }}
          >
            {isLogin ? "Sign Up" : "Sign In"}
          </button>
        </div>
      </div>
    </main>
  );
}