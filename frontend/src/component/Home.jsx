import React from "react";
import "../Home.css" // Make sure to create this CSS file
//D:\fitness-project-frontend\fitness-app\Home.css
const Home = () => {
  return (
    <div className="home-container">
      <header className="hero-section">
        <h1>🏋️‍♂️ ActiveFit</h1>
        <p>Your personal fitness companion</p>
      
      </header>

      <section className="features">
        <div className="feature-card">
          <h3>Track Activities</h3>
          <p>Log workouts, steps, and calories with ease.</p>
        </div>
        <div className="feature-card">
          <h3>Progress Insights</h3>
          <p>Visualize your fitness journey with smart analytics.</p>
        </div>
        <div className="feature-card">
          <h3>Secure Login</h3>
          <p>Powered by Keycloak for safe and smooth access.</p>
        </div>
      </section>

      <footer className="footer">
        <p>© 2025 ActiveFit. Stay strong, stay active.</p>
      </footer>
    </div>
  );
};

export default Home;
