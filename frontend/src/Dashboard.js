import "./App.css";

export default function Dashboard({ intake, goBackToQuestionnaire }) {
  const topics = intake?.responses?.topics || {};
  const entries = Object.entries(topics); // [ ["arrays_strings", 3], ... ]

  // Find weakest topic (lowest rating)
  let weakest = null;
  for (const [k, v] of entries) {
    if (weakest === null || v < weakest.value) weakest = { key: k, value: v };
  }

  const niceName = (key) => {
    const map = {
      arrays_strings: "Arrays & Strings",
      two_pointers: "Two Pointers",
      hash_maps_sets: "Hash Maps / Sets"
    };
    return map[key] || key;
  };

  return (
    <main className="container">
      <header className="header">
        <h1>SmartPrep</h1>
        <p className="subtitle">Dashboard (Sprint 2 Demo)</p>
      </header>

      <div className="card">
        <h2 style={{ marginTop: 0 }}>Your Profile</h2>
        <p>
          <strong>Experience:</strong>{" "}
          {intake?.responses?.experience_level || "—"}
        </p>

        <h3>Topic Ratings</h3>
        {entries.length === 0 ? (
          <p>No topic ratings yet.</p>
        ) : (
          <ul>
            {entries.map(([k, v]) => (
              <li key={k}>
                {niceName(k)}: <strong>{v}/5</strong>
              </li>
            ))}
          </ul>
        )}

        <p>
          <strong>Recommended next topic:</strong>{" "}
          {weakest ? `${niceName(weakest.key)} (${weakest.value}/5)` : "—"}
        </p>
      </div>

      <div className="row">
        <button className="secondary" type="button" onClick={goBackToQuestionnaire}>
          Back to Questionnaire
        </button>
      </div>
    </main>
  );
}