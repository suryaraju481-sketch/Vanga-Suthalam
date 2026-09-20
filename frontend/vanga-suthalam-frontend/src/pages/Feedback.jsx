import React, { useState } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import "./Feedback.css";

const FEEDBACK_API =
  "http://localhost:8090/VangaSuthalam1/api/feedback";

const ratingItems = [
  {
    key: "captainRating",
    icon: "🧑‍✈️",
    title: "Captain",
    text: "Professionalism and service",
  },
  {
    key: "boatRating",
    icon: "🚤",
    title: "Boat",
    text: "Condition and comfort",
  },
  {
    key: "foodRating",
    icon: "🍽️",
    title: "Food",
    text: "Quality and dining experience",
  },
  {
    key: "safetyRating",
    icon: "🛟",
    title: "Safety",
    text: "Safety and security",
  },
  {
    key: "experienceRating",
    icon: "🏝️",
    title: "Experience",
    text: "Sea and island adventure",
  },
];

const ratingNames = {
  1: "Poor",
  2: "Fair",
  3: "Good",
  4: "Very Good",
  5: "Excellent",
};

function StarRating({ value, onChange }) {
  return (
    <div className="vs-stars-wrapper">
      <div className="vs-stars">
        {[1, 2, 3, 4, 5].map((star) => (
          <button
            key={star}
            type="button"
            className={`vs-star ${
              star <= value ? "active" : ""
            }`}
            onClick={() => onChange(star)}
            aria-label={`Rate ${star} stars`}
          >
            ★
          </button>
        ))}
      </div>

      <span className="vs-rating-name">
        {value > 0 ? ratingNames[value] : "Select rating"}
      </span>
    </div>
  );
}

function Feedback() {
  const location = useLocation();
  const navigate = useNavigate();

  /* ---------------------------------------------------------
     BOOKING DATA FROM PAYMENT SUCCESS
     --------------------------------------------------------- */

  const booking = location.state?.booking || null;
  const customer = location.state?.customer || null;

  const bookingId =
    booking?.bookingId ||
    localStorage.getItem("bookingId") ||
    "";

  const customerId =
    customer?.customerId ||
    localStorage.getItem("customerId") ||
    "";

  const customerName =
    customer?.name ||
    localStorage.getItem("customerName") ||
    "Vanga Suthalam Customer";

  const customerEmail =
    customer?.email ||
    localStorage.getItem("customerEmail") ||
    "";

  const destination =
    booking?.destination ||
    "Sea Adventure";

  const packageName =
    booking?.packageName ||
    "Vanga Suthalam Package";

  const people =
    booking?.numberOfPeople ||
    booking?.people ||
    1;

  const bookingDate =
    booking?.bookingDate ||
    "Not available";

  /* ---------------------------------------------------------
     STATE
     --------------------------------------------------------- */

  const [ratings, setRatings] = useState({
    captainRating: 0,
    boatRating: 0,
    foodRating: 0,
    safetyRating: 0,
    experienceRating: 0,
    overallRating: 0,
  });

  const [comments, setComments] = useState("");

  const [errors, setErrors] = useState({});

  const [loading, setLoading] = useState(false);

  const [success, setSuccess] = useState(false);

  const [feedbackId, setFeedbackId] = useState("");

  /* ---------------------------------------------------------
     UPDATE RATING
     --------------------------------------------------------- */

  const updateRating = (field, value) => {
    setRatings((previous) => ({
      ...previous,
      [field]: value,
    }));

    setErrors((previous) => ({
      ...previous,
      [field]: "",
    }));
  };

  /* ---------------------------------------------------------
     VALIDATION
     --------------------------------------------------------- */

  const validateForm = () => {
    const newErrors = {};

    if (!bookingId) {
      newErrors.bookingId =
        "Booking information is missing. Please open feedback from your completed booking.";
    }

    if (!customerId) {
      newErrors.customerId =
        "Customer information is missing. Please login again.";
    }

    ratingItems.forEach((item) => {
      if (ratings[item.key] === 0) {
        newErrors[item.key] =
          `Please rate the ${item.title.toLowerCase()}.`;
      }
    });

    if (ratings.overallRating === 0) {
      newErrors.overallRating =
        "Please select your overall rating.";
    }

    if (!comments.trim()) {
      newErrors.comments =
        "Please enter your experience.";
    } else if (comments.trim().length < 10) {
      newErrors.comments =
        "Please enter at least 10 characters.";
    }

    setErrors(newErrors);

    return Object.keys(newErrors).length === 0;
  };

  /* ---------------------------------------------------------
     SUBMIT TO JAVA BACKEND
     --------------------------------------------------------- */

  const submitFeedback = async (event) => {
    event.preventDefault();

    if (!validateForm()) {
      window.scrollTo({
        top: 300,
        behavior: "smooth",
      });

      return;
    }

    try {
      setLoading(true);

      const body = new URLSearchParams();

      body.append("bookingId", String(bookingId));
      body.append("customerId", String(customerId));

      body.append(
        "captainRating",
        String(ratings.captainRating)
      );

      body.append(
        "boatRating",
        String(ratings.boatRating)
      );

      body.append(
        "foodRating",
        String(ratings.foodRating)
      );

      body.append(
        "safetyRating",
        String(ratings.safetyRating)
      );

      body.append(
        "experienceRating",
        String(ratings.experienceRating)
      );

      body.append(
        "overallRating",
        String(ratings.overallRating)
      );

      body.append(
        "comments",
        comments.trim()
      );

      const response = await fetch(FEEDBACK_API, {
        method: "POST",
        headers: {
          "Content-Type":
            "application/x-www-form-urlencoded",
        },
        body: body.toString(),
      });

      let data;

      try {
        data = await response.json();
      } catch {
        throw new Error(
          "Invalid response from feedback server."
        );
      }

      if (!response.ok || !data.success) {
        throw new Error(
          data.message ||
            "Feedback submission failed."
        );
      }

      if (data.feedbackId) {
        setFeedbackId(
          String(data.feedbackId)
        );

        localStorage.setItem(
          "feedbackId",
          String(data.feedbackId)
        );
      }

      setSuccess(true);

      window.scrollTo({
        top: 0,
        behavior: "smooth",
      });

    } catch (error) {
      console.error(
        "Feedback Error:",
        error
      );

      setErrors({
        submit:
          error.message ||
          "Unable to connect to feedback server.",
      });

    } finally {
      setLoading(false);
    }
  };

  /* ---------------------------------------------------------
     SUCCESS PAGE
     --------------------------------------------------------- */

  if (success) {
    return (
      <div className="vs-feedback-page">

        <nav className="vs-feedback-navbar">

          <Link
            to="/"
            className="vs-feedback-logo"
          >
            🌊 Vanga Suthalam
          </Link>

          <Link
            to="/"
            className="vs-home-link"
          >
            Home
          </Link>

        </nav>

        <section className="vs-success-hero">

          <div className="vs-success-overlay"></div>

          <div className="vs-success-content">

            <div className="vs-success-check">
              ✓
            </div>

            <span className="vs-success-badge">
              FEEDBACK SUBMITTED
            </span>

            <h1>
              Thank You,
              <br />
              {customerName}!
            </h1>

            <p>
              Your feedback has been successfully
              submitted. Thank you for sharing your
              Vanga Suthalam experience with us.
            </p>

          </div>

        </section>

        <main className="vs-success-container">

          <section className="vs-success-card">

            <div className="vs-success-heading">

              <div className="vs-success-icon">
                ⭐
              </div>

              <div>

                <span>
                  CUSTOMER EXPERIENCE
                </span>

                <h2>
                  Your review matters
                </h2>

                <p>
                  Your feedback helps us improve
                  our boats, captains, safety and
                  guest experience.
                </p>

              </div>

            </div>

            {feedbackId && (
              <div className="vs-feedback-id">

                <span>
                  FEEDBACK ID
                </span>

                <strong>
                  #{feedbackId}
                </strong>

              </div>
            )}

            <div className="vs-trip-summary">

              <div className="vs-trip-icon">
                🚤
              </div>

              <div>

                <span>
                  YOUR TRIP
                </span>

                <h3>
                  {destination}
                </h3>

                <p>
                  {packageName}
                  {" • "}
                  {people} Guest(s)
                  {" • "}
                  {bookingDate}
                </p>

              </div>

            </div>

            <div className="vs-success-actions">

              <button
                type="button"
                onClick={() => navigate("/")}
                className="vs-primary-button"
              >
                ← Back to Home
              </button>

              <button
                type="button"
                onClick={() => navigate("/destinations")}
                className="vs-secondary-button"
              >
                Explore Destinations
              </button>

            </div>

          </section>

        </main>

        <footer className="vs-feedback-footer">

          <strong>
            🌊 Vanga Suthalam
          </strong>

          <span>
            Ramanathapuram & Rameswaram
          </span>

          <small>
            © 2026 Vanga Suthalam • Explore Responsibly
          </small>

        </footer>

      </div>
    );
  }

  /* ---------------------------------------------------------
     FEEDBACK PAGE
     --------------------------------------------------------- */

  return (
    <div className="vs-feedback-page">

      <nav className="vs-feedback-navbar">

        <Link
          to="/"
          className="vs-feedback-logo"
        >
          🌊 Vanga Suthalam
        </Link>

        <Link
          to="/"
          className="vs-home-link"
        >
          ← Home
        </Link>

      </nav>

      {/* HERO */}

      <section className="vs-feedback-hero">

        <div className="vs-feedback-hero-overlay"></div>

        <div className="vs-feedback-hero-content">

          <span className="vs-hero-badge">
            ⭐ CUSTOMER EXPERIENCE
          </span>

          <h1>
            Share Your
            <br />
            <span>Island Adventure</span>
          </h1>

          <p>
            Your experience matters. Tell us about
            your boat journey, captain, safety,
            food and island adventure.
          </p>

          <div className="vs-hero-items">

            <div>
              <span>🚤</span>
              <strong>Boat Journey</strong>
            </div>

            <div>
              <span>🏝️</span>
              <strong>Island Explorer</strong>
            </div>

            <div>
              <span>⭐</span>
              <strong>Guest Experience</strong>
            </div>

          </div>

        </div>

      </section>


      <main className="vs-feedback-container">

        {/* BOOKING */}

        <section className="vs-booking-card">

          <div className="vs-booking-icon">
            🚤
          </div>

          <div className="vs-booking-content">

            <span>
              YOUR COMPLETED TRIP
            </span>

            <h2>
              {destination}
            </h2>

            <div className="vs-booking-details">

              <span>
                📦 {packageName}
              </span>

              <span>
                👥 {people} Guest(s)
              </span>

              <span>
                📅 {bookingDate}
              </span>

            </div>

          </div>

          <div className="vs-booking-number">

            <span>
              BOOKING
            </span>

            <strong>
              #{bookingId || "N/A"}
            </strong>

          </div>

        </section>


        {/* BOOKING ERROR */}

        {(errors.bookingId ||
          errors.customerId) && (

          <div className="vs-warning">

            <div>
              ⚠️
            </div>

            <section>

              <strong>
                Booking information required
              </strong>

              <p>
                {errors.bookingId ||
                  errors.customerId}
              </p>

            </section>

          </div>

        )}


        <form
          className="vs-feedback-form"
          onSubmit={submitFeedback}
          noValidate
        >

          {/* RATINGS */}

          <section className="vs-section">

            <div className="vs-section-heading">

              <div className="vs-section-number">
                01
              </div>

              <div>

                <span>
                  YOUR REVIEW
                </span>

                <h2>
                  Rate Your Experience
                </h2>

                <p>
                  Rate every important part of
                  your Vanga Suthalam journey.
                </p>

              </div>

            </div>


            <div className="vs-rating-grid">

              {ratingItems.map((item) => (

                <div
                  key={item.key}
                  className={`vs-rating-card ${
                    errors[item.key]
                      ? "vs-rating-error"
                      : ""
                  }`}
                >

                  <div className="vs-rating-header">

                    <div className="vs-rating-icon">
                      {item.icon}
                    </div>

                    <div>

                      <h3>
                        {item.title}
                      </h3>

                      <p>
                        {item.text}
                      </p>

                    </div>

                  </div>


                  <StarRating
                    value={ratings[item.key]}
                    onChange={(value) =>
                      updateRating(
                        item.key,
                        value
                      )
                    }
                  />


                  {errors[item.key] && (
                    <small className="vs-field-error">
                      {errors[item.key]}
                    </small>
                  )}

                </div>

              ))}

            </div>

          </section>


          {/* OVERALL */}

          <section className="vs-overall-card">

            <div className="vs-overall-left">

              <div className="vs-overall-icon">
                ⭐
              </div>

              <div>

                <span>
                  FINAL RATING
                </span>

                <h2>
                  Overall Experience
                </h2>

                <p>
                  Rate your complete trip.
                </p>

              </div>

            </div>

            <div className="vs-overall-right">

              <StarRating
                value={ratings.overallRating}
                onChange={(value) =>
                  updateRating(
                    "overallRating",
                    value
                  )
                }
              />

              {errors.overallRating && (
                <small className="vs-field-error">
                  {errors.overallRating}
                </small>
              )}

            </div>

          </section>


          {/* COMMENTS */}

          <section className="vs-section">

            <div className="vs-section-heading">

              <div className="vs-section-number">
                02
              </div>

              <div>

                <span>
                  YOUR STORY
                </span>

                <h2>
                  Tell Us More
                </h2>

                <p>
                  Share your favourite moments
                  and suggestions.
                </p>

              </div>

            </div>


            <div
              className={`vs-comments-box ${
                errors.comments
                  ? "vs-comments-error"
                  : ""
              }`}
            >

              <div className="vs-comments-title">
                <span>💬</span>
                <strong>
                  Your Experience
                </strong>
              </div>

              <textarea
                value={comments}
                onChange={(event) => {
                  setComments(
                    event.target.value
                  );

                  setErrors((previous) => ({
                    ...previous,
                    comments: "",
                  }));
                }}
                maxLength={500}
                rows={7}
                placeholder="Tell us about your boat journey, captain, food, safety, island experience or anything you enjoyed..."
              />

              <div className="vs-comments-footer">

                <span>
                  Maximum 500 characters
                </span>

                <strong>
                  {comments.length}/500
                </strong>

              </div>

            </div>

            {errors.comments && (
              <small className="vs-field-error">
                {errors.comments}
              </small>
            )}

          </section>


          {/* CUSTOMER */}

          <section className="vs-customer-card">

            <div className="vs-customer-icon">
              👤
            </div>

            <div className="vs-customer-info">

              <span>
                FEEDBACK FROM
              </span>

              <h3>
                {customerName}
              </h3>

              <p>
                {customerEmail ||
                  "Logged-in customer"}
              </p>

            </div>

            <div className="vs-verified">
              ✓ Verified
            </div>

          </section>


          {/* SERVER ERROR */}

          {errors.submit && (

            <div className="vs-submit-error">

              <span>
                ⚠️
              </span>

              <div>

                <strong>
                  Feedback submission failed
                </strong>

                <p>
                  {errors.submit}
                </p>

              </div>

            </div>

          )}


          {/* SUBMIT */}

          <section className="vs-submit-card">

            <div className="vs-submit-info">

              <div className="vs-lock">
                🔒
              </div>

              <div>

                <strong>
                  Your feedback matters
                </strong>

                <p>
                  Your review helps us provide
                  better experiences.
                </p>

              </div>

            </div>


            <button
              type="submit"
              className="vs-submit-button"
              disabled={loading}
            >

              {loading ? (
                <>
                  <span className="vs-spinner"></span>
                  Submitting...
                </>
              ) : (
                <>
                  Submit Feedback
                  <span>→</span>
                </>
              )}

            </button>

          </section>

        </form>

      </main>


      <footer className="vs-feedback-footer">

        <strong>
          🌊 Vanga Suthalam
        </strong>

        <span>
          Ramanathapuram & Rameswaram
        </span>

        <small>
          © 2026 Vanga Suthalam • Explore Responsibly
        </small>

      </footer>

    </div>
  );
}

export default Feedback;