import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Destinations.css";

const API_URL =
  "https://vanga-suthalam.onrender.com/api/destinations";

const Destinations = () => {
  const navigate = useNavigate();

  const [destinations, setDestinations] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const controller = new AbortController();

    const loadDestinations = async () => {
      try {
        setLoading(true);
        setError("");

        const response = await fetch(API_URL, {
          method: "GET",
          signal: controller.signal,
        });

        if (!response.ok) {
          throw new Error(
            `Server error: ${response.status}`
          );
        }

const data = await response.json();

if (!data.success) {
  throw new Error(
    data.message || "Unable to load destinations."
  );
}

if (!Array.isArray(data.destinations)) {
  throw new Error(
    "Invalid destinations data received from server."
  );
}

setDestinations(data.destinations);
      } catch (err) {
        if (err.name !== "AbortError") {
          console.error(
            "Destination API error:",
            err
          );

          setError(
            "Unable to load destinations. Please check that the Java backend and Tomcat are running."
          );
        }
      } finally {
        if (!controller.signal.aborted) {
          setLoading(false);
        }
      }
    };

    loadDestinations();

    return () => {
      controller.abort();
    };
  }, []);

  // --------------------------------------------------
  // CHECK ISLAND DESTINATION
  // --------------------------------------------------

  const isIsland = (destination) => {
    return (
      String(
        destination.destinationType || ""
      ).toLowerCase() === "island explorer"
    );
  };

  // --------------------------------------------------
  // CHECK APPROVAL REQUIRED
  // --------------------------------------------------

  const isApprovalRequired = (destination) => {
    return isIsland(destination);
  };

  // --------------------------------------------------
  // OPEN ISLAND APPROVAL PAGE
  // --------------------------------------------------

  const handleIslandApproval = (destination) => {
    navigate("/island-approval", {
      state: {
        island: destination,
      },
    });
  };

  // --------------------------------------------------
  // NORMAL DESTINATION ACTION
  // --------------------------------------------------

  const handleExplore = (destination) => {
    navigate("/packages", {
      state: {
        destination: destination,
      },
    });
  };

  // --------------------------------------------------
  // SPLIT DESTINATIONS
  // --------------------------------------------------

  const seaDestinations = destinations.filter(
    (destination) => !isIsland(destination)
  );

  const islandDestinations = destinations.filter(
    (destination) => isIsland(destination)
  );

  return (
    <div className="destinations-page">

      {/* ================= NAVBAR ================= */}

      <nav className="destination-navbar">

        <div
          className="destination-logo"
          onClick={() => navigate("/")}
        >
          🌊 <span>VANGA SUTHALAM</span>
        </div>

        <div className="destination-nav-links">

          <button
            onClick={() => navigate("/")}
          >
            Home
          </button>

          <button
            className="active"
            onClick={() =>
              navigate("/destinations")
            }
          >
            Destinations
          </button>

          <button
            onClick={() =>
              navigate("/packages")
            }
          >
            Packages
          </button>

          <button
            onClick={() =>
              navigate("/feedback")
            }
          >
            Feedback
          </button>

        </div>

        <div className="destination-nav-actions">

          <button
            className="login-nav-btn"
            onClick={() =>
              navigate("/login")
            }
          >
            Login
          </button>

          <button
            className="register-nav-btn"
            onClick={() =>
              navigate("/register")
            }
          >
            Register
          </button>

        </div>

      </nav>

      {/* ================= HERO ================= */}

      <section className="destinations-hero">

        <div className="destinations-hero-overlay"></div>

        <div className="destinations-hero-content">

          <span className="hero-small-title">
            DISCOVER RAMANATHAPURAM & RAMESWARAM
          </span>

          <h1>
            Explore the <span>Blue Horizon</span>
          </h1>

          <p>
            Discover beautiful sea routes, marine
            adventures and approved island
            exploration experiences with
            VANGA SUTHALAM.
          </p>

          <div className="hero-buttons">

            <button
              className="hero-primary-btn"
              onClick={() =>
                document
                  .getElementById(
                    "sea-destinations"
                  )
                  ?.scrollIntoView({
                    behavior: "smooth",
                  })
              }
            >
              Explore Destinations
            </button>

            <button
              className="hero-secondary-btn"
              onClick={() =>
                navigate("/packages")
              }
            >
              View Packages
            </button>

          </div>

        </div>

      </section>

      {/* ================= LOADING ================= */}

      {loading && (
        <section className="destination-status">

          <div className="loading-spinner"></div>

          <h3>
            Loading destinations...
          </h3>

          <p>
            Connecting to VANGA SUTHALAM
            server.
          </p>

        </section>
      )}

      {/* ================= ERROR ================= */}

      {!loading && error && (
        <section className="destination-status error-status">

          <div className="error-icon">
            ⚠️
          </div>

          <h3>
            Unable to load destinations
          </h3>

          <p>
            {error}
          </p>

          <button
            onClick={() =>
              window.location.reload()
            }
          >
            Try Again
          </button>

        </section>
      )}

      {/* ================= CONTENT ================= */}

      {!loading && !error && (
        <>

          {/* ================= SEA ================= */}

          <section
            id="sea-destinations"
            className="destination-section"
          >

            <div className="section-heading">

              <span>
                🌊 SEA EXPLORATION
              </span>

              <h2>
                Explore Our Sea Destinations
              </h2>

              <p>
                Experience the beauty of the
                Ramanathapuram and Rameswaram
                coastline with trained local
                boat operators.
              </p>

            </div>

            {seaDestinations.length === 0 ? (

              <div className="empty-destinations">
                No sea destinations available.
              </div>

            ) : (

              <div className="destination-grid">

                {seaDestinations.map(
                  (destination) => (

                    <div
                      className="destination-card"
                      key={
                        destination.destinationId
                      }
                    >

                      <div className="destination-image">

                        <img
                          src="/image.png"
                          alt={
                            destination.destinationName ||
                            "Sea destination"
                          }
                        />

                        <span className="destination-type-badge">
                          🌊{" "}
                          {destination.destinationType ||
                            "Sea Explorer"}
                        </span>

                      </div>

                      <div className="destination-card-body">

                        <h3>
                          {
                            destination.destinationName
                          }
                        </h3>

                        <p className="destination-location">
                          📍{" "}
                          {destination.location ||
                            "Ramanathapuram"}
                        </p>

                        <p className="destination-description">
                          {
                            destination.description ||
                            "Explore the beautiful sea and coastal routes."
                          }
                        </p>

                        {destination.distanceKm && (
                          <div className="destination-info">
                            <span>
                              📏{" "}
                              {
                                destination.distanceKm
                              }{" "}
                              km
                            </span>
                          </div>
                        )}

                        <button
                          className="destination-explore-btn"
                          onClick={() =>
                            handleExplore(
                              destination
                            )
                          }
                        >
                          Explore Now →
                        </button>

                      </div>

                    </div>

                  )
                )}

              </div>

            )}

          </section>

          {/* ================= ISLAND ================= */}

          <section className="island-section">

            <div className="section-heading island-heading">

              <span>
                🏝️ ISLAND EXPLORER
              </span>

              <h2>
                Protected Island Experiences
              </h2>

              <p>
                Explore selected island
                destinations subject to applicable
                official approval and safety
                clearance.
              </p>

            </div>

            {/* PROTECTION NOTICE */}

            <div className="island-notice">

              <div className="notice-icon">
                🔒
              </div>

              <div>

                <h3>
                  Approval Required for Island
                  Exploration
                </h3>

                <p>
                  These island destinations are
                  displayed for exploration planning.
                  Applicable official permission,
                  certificates and safety clearance
                  must be completed before an island
                  trip can proceed.
                </p>

              </div>

            </div>

            {islandDestinations.length === 0 ? (

              <div className="empty-destinations">
                No island destinations available.
              </div>

            ) : (

              <div className="destination-grid island-grid">

                {islandDestinations.map(
                  (destination) => (

                    <div
                      className="destination-card island-card"
                      key={
                        destination.destinationId
                      }
                    >

                      {/* IMAGE */}

                      <div className="destination-image">

                        <img
                          src="/image.png"
                          alt={
                            destination.destinationName ||
                            "Island destination"
                          }
                        />

                        <span className="destination-type-badge island-badge">
                          🏝️ Island Explorer
                        </span>

                        <span className="approval-badge">
                          🔒 Approval Required
                        </span>

                      </div>

                      {/* CARD BODY */}

                      <div className="destination-card-body">

                        <h3>
                          {
                            destination.destinationName
                          }
                        </h3>

                        <p className="destination-location">
                          📍{" "}
                          {destination.location ||
                            "Ramanathapuram"}
                        </p>

                        <p className="destination-description">
                          {
                            destination.description ||
                            "Protected island exploration subject to applicable approval."
                          }
                        </p>

                        <div className="island-status-box">

                          <div>
                            <span className="status-label">
                              Trip Status
                            </span>

                            <strong>
                              Approval Required
                            </strong>
                          </div>

                          <div>
                            <span className="status-label">
                              Landing
                            </span>

                            <strong>
                              Subject to Permission
                            </strong>
                          </div>

                        </div>

                        <button
                          className="island-approval-btn"
                          onClick={() =>
                            handleIslandApproval(
                              destination
                            )
                          }
                        >
                          🔒 Apply for Approval
                        </button>

                      </div>

                    </div>

                  )
                )}

              </div>

            )}

          </section>

          {/* ================= SAFETY ================= */}

          <section className="safety-section">

            <div className="safety-content">

              <span className="safety-label">
                🛟 SAFETY FIRST
              </span>

              <h2>
                Your Safety Is Our Priority
              </h2>

              <p>
                VANGA SUTHALAM focuses on
                responsible sea exploration with
                trained captains, safety equipment,
                boat capacity checks and applicable
                clearance requirements.
              </p>

              <div className="safety-grid">

                <div className="safety-item">
                  <span>👨‍✈️</span>
                  <div>
                    <strong>
                      Trained Captains
                    </strong>
                    <small>
                      Experienced local boat
                      operators
                    </small>
                  </div>
                </div>

                <div className="safety-item">
                  <span>🦺</span>
                  <div>
                    <strong>
                      Safety Equipment
                    </strong>
                    <small>
                      Required safety equipment
                      checks
                    </small>
                  </div>
                </div>

                <div className="safety-item">
                  <span>🚤</span>
                  <div>
                    <strong>
                      Boat Verification
                    </strong>
                    <small>
                      Capacity and availability
                      checks
                    </small>
                  </div>
                </div>

                <div className="safety-item">
                  <span>📋</span>
                  <div>
                    <strong>
                      Approval Checks
                    </strong>
                    <small>
                      Applicable island permission
                    </small>
                  </div>
                </div>

              </div>

            </div>

          </section>

          {/* ================= CTA ================= */}

          <section className="destination-cta">

            <div>

              <span>
                READY FOR YOUR ADVENTURE?
              </span>

              <h2>
                Choose Your Next Sea Experience
              </h2>

              <p>
                Select a destination and discover
                the right VANGA SUTHALAM package
                for your journey.
              </p>

            </div>

            <button
              onClick={() =>
                navigate("/packages")
              }
            >
              View All Packages →
            </button>

          </section>

        </>
      )}

      {/* ================= FOOTER ================= */}

      <footer className="destination-footer">

        <div className="footer-logo">
          🌊 VANGA SUTHALAM
        </div>

        <p>
          Sea & Island Exploration from
          Ramanathapuram and Rameswaram.
        </p>

        <div className="footer-links">

          <button
            onClick={() => navigate("/")}
          >
            Home
          </button>

          <button
            onClick={() =>
              navigate("/packages")
            }
          >
            Packages
          </button>

          <button
            onClick={() =>
              navigate("/feedback")
            }
          >
            Feedback
          </button>

        </div>

        <div className="footer-bottom">
          © 2026 VANGA SUTHALAM. All rights
          reserved.
        </div>

      </footer>

    </div>
  );
};

export default Destinations;
