import React, { useEffect, useState } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import "./Packages.css";

const API_URL =
  "http://localhost:8090/VangaSuthalam1/api/packages";

function Packages() {
  const location = useLocation();
  const navigate = useNavigate();

  const [packages, setPackages] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const islandPackageMode =
    location.state?.islandPackage === true;

  const selectedIsland =
    location.state?.island || null;

  useEffect(() => {
    let cancelled = false;

    const loadPackages = async () => {
      try {
        setLoading(true);
        setError("");

        const response = await fetch(API_URL);

        if (!response.ok) {
          throw new Error(
            `Server returned ${response.status}`
          );
        }

        const data = await response.json();

        if (!data.success) {
          throw new Error(
            data.message || "Unable to load packages."
          );
        }

        if (!Array.isArray(data.packages)) {
          throw new Error(
            "Invalid package data received from server."
          );
        }

        if (!cancelled) {
          setPackages(data.packages);
        }
      } catch (err) {
        if (!cancelled) {
          setError(
            err.message ||
              "Unable to connect to Java backend."
          );
        }
      } finally {
        if (!cancelled) {
          setLoading(false);
        }
      }
    };

    loadPackages();

    return () => {
      cancelled = true;
    };
  }, []);

  const displayedPackages = islandPackageMode
    ? packages.filter((pkg) => {
        const packageName =
          (pkg.packageName || "").toLowerCase();

        const description =
          (pkg.description || "").toLowerCase();

        return (
          packageName.includes("island") ||
          description.includes("island")
        );
      })
    : packages;

const handleBook = (pkg) => {
  const approvalId =
    location.state?.approvalId ||
    localStorage.getItem("islandApprovalId") ||
    null;

  const island =
    selectedIsland ||
    location.state?.island ||
    null;

  const destination = island
    ? {
        destinationId: island.destinationId,
        destinationName: island.destinationName,
        location: island.location,
        destinationType: island.destinationType,
      }
    : null;

  navigate("/booking", {
    state: {
      package: {
        packageId: pkg.packageId,
        name: pkg.packageName,
        priceValue: Number(
          pkg.basePricePerPerson || 0
        ),
        durationDays: pkg.durationDays,
        durationNights: pkg.durationNights,
        description: pkg.description,
        fishingIncluded: pkg.fishingIncluded,
        foodIncluded: pkg.foodIncluded,
        isIslandPackage: islandPackageMode,
        approvalId: approvalId,
        island: island,
        destination: destination,
      },

      destination: destination,

      approvalId: approvalId,

      island: island,
    },
  });
};

  return (
    <div className="packages-page">

      {/* NAVBAR */}
      <nav className="packages-navbar">
        <div
          className="packages-logo"
          onClick={() => navigate("/")}
        >
          <span className="logo-icon">🌊</span>
          <span>VANGA SUTHALAM</span>
        </div>

        <div className="packages-nav-links">
          <Link to="/">Home</Link>
          <Link to="/destinations">
            Destinations
          </Link>

          <Link
            to="/packages"
            className="active"
          >
            Packages
          </Link>

          <Link to="/feedback">
            Feedback
          </Link>
        </div>

        <div className="packages-nav-actions">
          <Link
            to="/login"
            className="nav-login"
          >
            Login
          </Link>

          <Link
            to="/register"
            className="nav-register"
          >
            Register
          </Link>
        </div>
      </nav>

      {/* HERO */}
      <section className="packages-hero">
        <div className="packages-hero-overlay"></div>

        <div className="packages-hero-content">
          <span className="hero-small-title">
            🌊 VANGA SUTHALAM EXPERIENCES
          </span>

          <h1>
            Choose Your
            <br />
            <strong>Sea Adventure</strong>
          </h1>

          <p>
            Discover unforgettable sea and island
            experiences across Ramanathapuram and
            Rameswaram.
          </p>

          <div className="hero-actions">
            <a
              href="#packages"
              className="hero-primary-btn"
            >
              Explore Packages ↓
            </a>

            <Link
              to="/destinations"
              className="hero-secondary-btn"
            >
              View Destinations
            </Link>
          </div>
        </div>
      </section>

      {/* ISLAND MODE */}
      {islandPackageMode && (
        <section className="island-mode-banner">
          <div className="island-banner-icon">
            🏝️
          </div>

          <div className="island-banner-content">
            <span>
              APPROVAL CONFIRMED
            </span>

            <h2>
              {selectedIsland?.destinationName ||
                "Island Explorer"}
            </h2>

            <p>
              Your island approval has been
              confirmed. Select an eligible island
              package below.
            </p>
          </div>

          <div className="approval-badge">
            ✓ Approved
          </div>
        </section>
      )}

      {/* PACKAGE SECTION */}
      <section
        className="packages-section"
        id="packages"
      >
        <div className="section-heading">
          <span>
            {islandPackageMode
              ? "ISLAND EXPERIENCE"
              : "OUR PACKAGES"}
          </span>

          <h2>
            {islandPackageMode
              ? "Select Your Island Package"
              : "Explore Our Experiences"}
          </h2>

          <p>
            Carefully designed experiences for
            families, friends, tourists and sea
            adventure lovers.
          </p>
        </div>

        {/* LOADING */}
        {loading && (
          <div className="packages-state">
            <div className="loading-spinner"></div>
            <h3>Loading packages...</h3>
            <p>
              Please wait while we load the latest
              packages.
            </p>
          </div>
        )}

        {/* ERROR */}
        {!loading && error && (
          <div className="packages-state error-state">
            <div className="state-icon">
              ⚠️
            </div>

            <h3>
              Unable to load packages
            </h3>

            <p>{error}</p>

            <button
              onClick={() =>
                window.location.reload()
              }
              className="retry-btn"
            >
              Try Again
            </button>
          </div>
        )}

        {/* EMPTY */}
        {!loading &&
          !error &&
          displayedPackages.length === 0 && (
            <div className="packages-state">
              <div className="state-icon">
                🏝️
              </div>

              <h3>
                No packages available
              </h3>

              <p>
                No suitable package is currently
                available for this selection.
              </p>

              <Link
                to="/destinations"
                className="retry-btn"
              >
                ← Back to Destinations
              </Link>
            </div>
          )}

        {/* CARDS */}
        {!loading &&
          !error &&
          displayedPackages.length > 0 && (
            <div className="packages-grid">
              {displayedPackages.map(
                (pkg, index) => {
                  const price = Number(
                    pkg.basePricePerPerson || 0
                  );

                  const isIsland =
                    islandPackageMode ||
                    (pkg.packageName || "")
                      .toLowerCase()
                      .includes("island");

                  return (
                    <article
                      className={`package-card ${
                        isIsland
                          ? "island-package-card"
                          : ""
                      }`}
                      key={pkg.packageId}
                    >

                      {/* CARD TOP */}
                      <div className="package-image">

                        <img
                          src="/image.png"
                          alt={
                            pkg.packageName
                          }
                        />

                        <div className="image-overlay"></div>

                        <div className="package-number">
                          {String(
                            index + 1
                          ).padStart(2, "0")}
                        </div>

                        {isIsland && (
                          <div className="island-label">
                            🏝️ ISLAND
                          </div>
                        )}
                      </div>

                      {/* CARD BODY */}
                      <div className="package-content">

                        <div className="package-tag">
                          {pkg.durationDays === 2
                            ? "2 DAY ADVENTURE"
                            : "1 DAY ADVENTURE"}
                        </div>

                        <h3>
                          {pkg.packageName}
                        </h3>

                        <p className="package-description">
                          {pkg.description ||
                            "Enjoy a memorable sea adventure with Vanga Suthalam."}
                        </p>

                        {/* DURATION */}
                        <div className="package-info-row">

                          <div className="package-info">
                            <span className="info-icon">
                              ⏱
                            </span>

                            <div>
                              <small>
                                Duration
                              </small>

                              <strong>
                                {
                                  pkg.durationDays
                                }{" "}
                                Day
                                {pkg.durationDays >
                                1
                                  ? "s"
                                  : ""}
                              </strong>
                            </div>
                          </div>

                          <div className="package-info">
                            <span className="info-icon">
                              🌙
                            </span>

                            <div>
                              <small>
                                Nights
                              </small>

                              <strong>
                                {
                                  pkg.durationNights ||
                                    0
                                }
                              </strong>
                            </div>
                          </div>

                        </div>

                        {/* INCLUDED */}
                        <div className="included-section">

                          <span className="included-title">
                            PACKAGE INCLUDES
                          </span>

                          <div className="included-list">

                            {pkg.fishingIncluded && (
                              <span>
                                ✓ Fishing
                              </span>
                            )}

                            {pkg.foodIncluded && (
                              <span>
                                ✓ Food
                              </span>
                            )}

                            <span>
                              ✓ Boat Ride
                            </span>

                            <span>
                              ✓ Safety Support
                            </span>

                          </div>
                        </div>

                        {/* PRICE */}
                        <div className="package-bottom">

                          <div className="price-area">
                            <small>
                              Starting from
                            </small>

                            <div className="price">
                              ₹
                              {price.toLocaleString(
                                "en-IN"
                              )}
                            </div>

                            <span>
                              per person
                            </span>
                          </div>

                          <button
                            className="package-book-btn"
                            onClick={() =>
                              handleBook(pkg)
                            }
                          >
                            Book Now
                            <span>→</span>
                          </button>

                        </div>

                      </div>
                    </article>
                  );
                }
              )}
            </div>
          )}
      </section>

      {/* WHY CHOOSE US */}
      <section className="why-section">

        <div className="section-heading light-heading">
          <span>
            WHY VANGA SUTHALAM
          </span>

          <h2>
            Your Safety. Our Priority.
          </h2>

          <p>
            Every experience is designed around
            safety, comfort and unforgettable
            memories.
          </p>
        </div>

        <div className="why-grid">

          <div className="why-card">
            <div className="why-icon">
              🛟
            </div>

            <h3>
              Safety First
            </h3>

            <p>
              Safety checks, life jackets,
              emergency equipment and weather
              verification are part of our trip
              process.
            </p>
          </div>

          <div className="why-card">
            <div className="why-icon">
              🚤
            </div>

            <h3>
              Experienced Captains
            </h3>

            <p>
              Trips are managed with trained
              boat operators and proper boat
              capacity verification.
            </p>
          </div>

          <div className="why-card">
            <div className="why-icon">
              🏝️
            </div>

            <h3>
              Responsible Exploration
            </h3>

            <p>
              Island experiences are subject to
              applicable permissions, protected-area
              rules and safety clearance.
            </p>
          </div>

          <div className="why-card">
            <div className="why-icon">
              ⭐
            </div>

            <h3>
              Memorable Experience
            </h3>

            <p>
              Enjoy sea exploration, fishing,
              food and beautiful coastal
              experiences.
            </p>
          </div>

        </div>
      </section>

      {/* ISLAND NOTICE */}
      <section className="island-notice-section">

        <div className="island-notice-card">

          <div className="notice-icon">
            🔒
          </div>

          <div className="notice-content">

            <span>
              IMPORTANT
            </span>

            <h3>
              Island Exploration & Approval
            </h3>

            <p>
              Some islands and protected marine
              areas may require official approval
              before travel. Landing permissions
              can also be restricted. Please use
              only routes and packages permitted
              for your trip.
            </p>

          </div>

          <Link
            to="/destinations"
            className="notice-btn"
          >
            View Islands →
          </Link>

        </div>
      </section>

      {/* CTA */}
      <section className="packages-cta">

        <div className="cta-overlay"></div>

        <div className="cta-content">

          <span>
            READY FOR THE SEA?
          </span>

          <h2>
            Your Adventure
            <br />
            Starts Here.
          </h2>

          <p>
            Choose your destination, select your
            experience and get ready to explore.
          </p>

          <Link
            to="/destinations"
            className="cta-button"
          >
            Explore Destinations →
          </Link>

        </div>
      </section>

      {/* FOOTER */}
      <footer className="packages-footer">

        <div className="footer-main">

          <div className="footer-brand">
            <div className="footer-logo">
              🌊 VANGA SUTHALAM
            </div>

            <p>
              Sea and island exploration
              experiences around Ramanathapuram
              and Rameswaram.
            </p>
          </div>

          <div className="footer-column">
            <h4>
              Explore
            </h4>

            <Link to="/">
              Home
            </Link>

            <Link to="/destinations">
              Destinations
            </Link>

            <Link to="/packages">
              Packages
            </Link>
          </div>

          <div className="footer-column">
            <h4>
              Support
            </h4>

            <Link to="/login">
              Login
            </Link>

            <Link to="/register">
              Register
            </Link>

            <Link to="/feedback">
              Feedback
            </Link>
          </div>

        </div>

        <div className="footer-bottom">
          <span>
            © 2026 Vanga Suthalam. All rights reserved.
          </span>

          <span>
            Ramanathapuram • Rameswaram
          </span>
        </div>

      </footer>

    </div>
  );
}

export default Packages;