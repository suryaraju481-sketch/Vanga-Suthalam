import React from "react";
import { useNavigate } from "react-router-dom";
import "./About.css";

function About() {
  const navigate = useNavigate();

  return (
    <div className="about-page">

      {/* ================= NAVBAR ================= */}

      <nav className="about-navbar">

        <div
          className="about-logo"
          onClick={() => navigate("/")}
        >
          🌊 VANGA SUTHALAM
        </div>

        <div className="about-nav-links">

          <button onClick={() => navigate("/")}>
            Home
          </button>

          <button onClick={() => navigate("/destinations")}>
            Destinations
          </button>

          <button onClick={() => navigate("/packages")}>
            Packages
          </button>

          <button onClick={() => navigate("/about")}>
            About
          </button>

          <button onClick={() => navigate("/feedback")}>
            Feedback
          </button>

        </div>

        <div className="about-nav-actions">

          <button
            className="about-login-btn"
            onClick={() => navigate("/login")}
          >
            Login
          </button>

          <button
            className="about-register-btn"
            onClick={() => navigate("/register")}
          >
            Register
          </button>

        </div>

      </nav>


      {/* ================= HERO ================= */}

      <section className="about-hero">

        <div className="about-hero-overlay"></div>

        <div className="about-hero-content">

          <span>🌊 VANGA SUTHALAM</span>

          <h1>
            Explore Beyond
            <strong> The Shore</strong>
          </h1>

          <p>
            Discover the beautiful sea and island
            experiences of Ramanathapuram and
            Rameswaram.
          </p>

        </div>

      </section>


      {/* ================= INTRODUCTION ================= */}

      <section className="about-intro">

        <div className="about-intro-image">

          <img
            src="https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=1200&q=90"
            alt="Sea adventure"
          />

        </div>

        <div className="about-intro-content">

          <span className="about-section-label">
            ABOUT VANGA SUTHALAM
          </span>

          <h2>
            Your Gateway to
            <span> Sea Adventures</span>
          </h2>

          <p>
            Vanga Suthalam is a sea and island exploration
            platform designed to connect travelers with
            exciting marine experiences around
            Ramanathapuram and Rameswaram.
          </p>

          <p>
            Our platform brings destinations, boats,
            captains, packages, bookings, payments and
            safety processes together in one convenient
            system.
          </p>

          <p>
            From exploring beautiful islands to enjoying
            fishing and food experiences, Vanga Suthalam
            helps customers plan their journey through a
            simple and organized booking process.
          </p>

          <button
            className="about-explore-btn"
            onClick={() => navigate("/destinations")}
          >
            Explore Destinations →
          </button>

        </div>

      </section>


      {/* ================= FEATURES ================= */}

      <section className="about-features">

        <div className="about-heading">

          <span>
            WHAT WE PROVIDE
          </span>

          <h2>
            Everything You Need for
            <strong> Your Sea Journey</strong>
          </h2>

          <p>
            A complete platform for planning,
            booking and experiencing your
            sea adventure.
          </p>

        </div>


        <div className="about-feature-grid">

          <div className="about-feature-card">

            <div className="feature-icon">
              🏝️
            </div>

            <h3>
              Island Exploration
            </h3>

            <p>
              Discover unique island destinations
              around Ramanathapuram and explore
              unforgettable sea experiences.
            </p>

          </div>


          <div className="about-feature-card">

            <div className="feature-icon">
              🚤
            </div>

            <h3>
              Boat Experiences
            </h3>

            <p>
              Choose suitable boats and enjoy
              organized trips with trained captains
              and required safety equipment.
            </p>

          </div>


          <div className="about-feature-card">

            <div className="feature-icon">
              🎣
            </div>

            <h3>
              Fishing Experiences
            </h3>

            <p>
              Enjoy fishing activities as part of
              selected sea adventure packages.
            </p>

          </div>


          <div className="about-feature-card">

            <div className="feature-icon">
              🍽️
            </div>

            <h3>
              Food Packages
            </h3>

            <p>
              Selected packages can include food
              experiences during your sea journey.
            </p>

          </div>


          <div className="about-feature-card">

            <div className="feature-icon">
              🔒
            </div>

            <h3>
              Island Approval
            </h3>

            <p>
              Protected island destinations follow
              an approval process before customers
              can make an island booking.
            </p>

          </div>


          <div className="about-feature-card">

            <div className="feature-icon">
              🛡️
            </div>

            <h3>
              Safety Verification
            </h3>

            <p>
              Safety verification and trained captains
              are part of the journey management process.
            </p>

          </div>

        </div>

      </section>


      {/* ================= ISLAND EXPLORER ================= */}

      <section className="about-islands">

        <div className="about-heading light">

          <span>
            ISLAND EXPLORER
          </span>

          <h2>
            Discover Our Island Destinations
          </h2>

          <p>
            Explore the special island destinations
            available through Vanga Suthalam.
          </p>

        </div>


        <div className="island-name-grid">

          <div className="island-name-card">
            <span>01</span>
            <h3>Mulli Theevu</h3>
            <p>Island Explorer</p>
          </div>

          <div className="island-name-card">
            <span>02</span>
            <h3>Desert Island</h3>
            <p>Island Explorer</p>
          </div>

          <div className="island-name-card">
            <span>03</span>
            <h3>Appa Theevu</h3>
            <p>Island Explorer</p>
          </div>

          <div className="island-name-card">
            <span>04</span>
            <h3>Valai Theevu</h3>
            <p>Island Explorer</p>
          </div>

          <div className="island-name-card">
            <span>05</span>
            <h3>Muyal Theevu</h3>
            <p>Island Explorer</p>
          </div>

        </div>

      </section>


      {/* ================= HOW IT WORKS ================= */}

      <section className="about-process">

        <div className="about-heading">

          <span>
            SIMPLE PROCESS
          </span>

          <h2>
            How Vanga Suthalam Works
          </h2>

        </div>


        <div className="process-grid">

          <div className="process-card">

            <div className="process-number">
              01
            </div>

            <h3>
              Choose Destination
            </h3>

            <p>
              Browse available sea and island
              destinations.
            </p>

          </div>


          <div className="process-card">

            <div className="process-number">
              02
            </div>

            <h3>
              Select Package
            </h3>

            <p>
              Choose a package that matches
              your travel experience.
            </p>

          </div>


          <div className="process-card">

            <div className="process-number">
              03
            </div>

            <h3>
              Complete Booking
            </h3>

            <p>
              Enter your journey details and
              confirm your booking.
            </p>

          </div>


          <div className="process-card">

            <div className="process-number">
              04
            </div>

            <h3>
              Enjoy Your Journey
            </h3>

            <p>
              Complete the required safety and
              approval process and enjoy your
              sea adventure.
            </p>

          </div>

        </div>

      </section>


      {/* ================= CTA ================= */}

      <section className="about-cta">

        <div className="about-cta-overlay"></div>

        <div className="about-cta-content">

          <span>
            🌊 YOUR NEXT ADVENTURE
          </span>

          <h2>
            The Sea Is Waiting
          </h2>

          <p>
            Start exploring the beautiful destinations
            around Ramanathapuram and Rameswaram.
          </p>

          <button
            onClick={() => navigate("/destinations")}
          >
            Start Exploring →
          </button>

        </div>

      </section>


      {/* ================= FOOTER ================= */}

      <footer className="about-footer">

        <div className="footer-brand">
          🌊 VANGA SUTHALAM
        </div>

        <p>
          Sea and island exploration experiences
          around Ramanathapuram and Rameswaram.
        </p>

        <div className="footer-links">

          <button onClick={() => navigate("/")}>
            Home
          </button>

          <button onClick={() => navigate("/destinations")}>
            Destinations
          </button>

          <button onClick={() => navigate("/packages")}>
            Packages
          </button>

          <button onClick={() => navigate("/contact")}>
            Contact
          </button>

        </div>

        <span>
          © 2026 Vanga Suthalam. All rights reserved.
        </span>

      </footer>

    </div>
  );
}

export default About;