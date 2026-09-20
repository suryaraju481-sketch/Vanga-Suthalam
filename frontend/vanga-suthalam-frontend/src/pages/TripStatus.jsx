import React from "react";
import { useLocation, useNavigate } from "react-router-dom";
import "./TripStatus.css";

const API_BASE = "http://localhost:8090/VangaSuthalam1";

function TripStatus() {
  const location = useLocation();
  const navigate = useNavigate();

  const trip = location.state?.trip || {};

  const tripId =
    trip.tripId || localStorage.getItem("activeTripId");

  const bookingId =
    trip.bookingId || localStorage.getItem("activeBookingId");

  const [message, setMessage] = React.useState("");
  const [loading, setLoading] = React.useState(false);

  const handleCompleteTrip = async () => {
    if (!tripId) {
      setMessage("Trip ID is missing.");
      return;
    }

    setLoading(true);
    setMessage("");

    try {
      const formData = new URLSearchParams();
      formData.append("tripId", String(tripId));

      const response = await fetch(
        `${API_BASE}/api/complete-trip`,
        {
          method: "POST",
          headers: {
            "Content-Type":
              "application/x-www-form-urlencoded",
          },
          body: formData.toString(),
        }
      );

      const data = await response.json();

      console.log("Complete Trip Response:", data);

      if (!response.ok || !data.success) {
        setMessage(
          data.message ||
            "Unable to complete the trip."
        );
        setLoading(false);
        return;
      }

      // Clear active trip information
      localStorage.removeItem("activeTripId");
      localStorage.removeItem("activeBookingId");

      // Prepare feedback data
      const feedbackBookingId =
        data.bookingId || bookingId;

      // Go directly to Feedback
      navigate("/feedback", {
        state: {
          trip: data,
          bookingId: feedbackBookingId,
        },
      });

    } catch (error) {
      console.error(
        "Complete Trip Error:",
        error
      );

      setMessage(
        "Unable to connect to Complete Trip API."
      );

      setLoading(false);
    }
  };

  const handleDashboard = () => {
    navigate("/captain-dashboard");
  };

  return (
    <div className="trip-status-page">

      <div className="trip-overlay"></div>

      <div className="trip-status-container">

        {/* Header */}
        <div className="trip-status-header">

          <div className="trip-logo">
            🚤
          </div>

          <div>
            <h1>Vanga Suthalam</h1>
            <p>Sea & Island Exploration</p>
          </div>

        </div>

        {/* Live Status */}
        <div className="live-status">
          <span className="live-dot"></span>
          TRIP IN PROGRESS
        </div>

        {/* Main Card */}
        <div className="trip-main-card">

          <div className="success-icon">
            🚤
          </div>

          <h2>
            Your Journey Is In Progress
          </h2>

          <p className="trip-description">
            Your boat trip has started
            successfully. Please enjoy your
            journey safely.
          </p>

          {/* Trip Information */}
          <div className="trip-info-grid">

            <div className="info-box">
              <span className="info-icon">
                🎫
              </span>

              <div>
                <label>Trip ID</label>
                <strong>
                  {tripId || "N/A"}
                </strong>
              </div>
            </div>

            <div className="info-box">
              <span className="info-icon">
                📋
              </span>

              <div>
                <label>Booking ID</label>
                <strong>
                  {bookingId || "N/A"}
                </strong>
              </div>
            </div>

            <div className="info-box">
              <span className="info-icon">
                👨‍✈️
              </span>

              <div>
                <label>Captain ID</label>
                <strong>
                  {trip.captainId || "N/A"}
                </strong>
              </div>
            </div>

            <div className="info-box">
              <span className="info-icon">
                🚤
              </span>

              <div>
                <label>Boat ID</label>
                <strong>
                  {trip.boatId || "N/A"}
                </strong>
              </div>
            </div>

          </div>

          {/* Journey Status */}
          <div className="journey-section">

            <h3>Journey Status</h3>

            <div className="journey-line">

              <div className="journey-step completed">
                <div className="step-circle">
                  ✓
                </div>
                <span>Booking</span>
              </div>

              <div className="progress-line active"></div>

              <div className="journey-step completed">
                <div className="step-circle">
                  ✓
                </div>
                <span>Payment</span>
              </div>

              <div className="progress-line active"></div>

              <div className="journey-step completed">
                <div className="step-circle">
                  ✓
                </div>
                <span>Safety</span>
              </div>

              <div className="progress-line active"></div>

              <div className="journey-step current">
                <div className="step-circle">
                  🚤
                </div>
                <span>Trip</span>
              </div>

            </div>

          </div>

          {/* Start Time */}
          <div className="start-time-box">

            <span>🕐</span>

            <div>
              <label>Trip Started</label>

              <strong>
                {trip.tripStart
                  ? new Date(
                      trip.tripStart
                    ).toLocaleString()
                  : "Trip is currently active"}
              </strong>
            </div>

          </div>

          {/* Message */}
          {message && (
            <div className="trip-message error-message">
              {message}
            </div>
          )}

          {/* Buttons */}
          <div className="trip-actions">

            <button
              className="complete-trip-btn"
              onClick={handleCompleteTrip}
              disabled={loading}
            >
              {loading
                ? "Completing Trip..."
                : "✓ Complete Trip"}
            </button>

            <button
              className="dashboard-btn"
              onClick={handleDashboard}
            >
              ← Captain Dashboard
            </button>

          </div>

        </div>

        {/* Safety Notice */}
        <div className="safety-notice">

          <span>🛟</span>

          <div>
            <strong>Safety First</strong>

            <p>
              Follow your captain's instructions
              and remain inside the designated
              safe area throughout the journey.
            </p>
          </div>

        </div>

        {/* Footer */}
        <div className="trip-footer">
          Vanga Suthalam © 2026 • Safe Sea Exploration
        </div>

      </div>
    </div>
  );
}

export default TripStatus;