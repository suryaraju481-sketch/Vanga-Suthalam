import React, { useState } from "react";
import "./TripManagement.css";

const API_BASE = "http://localhost:8090/VangaSuthalam1";

function TripManagement() {

  const [bookingId, setBookingId] = useState("");
  const [loading, setLoading] = useState(false);

  const [tripData, setTripData] = useState(null);
  const [message, setMessage] = useState("");
  const [messageType, setMessageType] = useState("");

  // ==============================
  // START / COMPLETE TRIP
  // ==============================

  const handleTripAction = async (action) => {

    setMessage("");
    setMessageType("");
    setTripData(null);

    if (!bookingId.trim()) {
      setMessage("Please enter Booking ID.");
      setMessageType("error");
      return;
    }

    setLoading(true);

    try {

      const formData = new URLSearchParams();

      formData.append("bookingId", bookingId);
      formData.append("action", action);

      const response = await fetch(
        `${API_BASE}/api/trips`,
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

      if (!response.ok || !data.success) {

        throw new Error(
          data.message ||
          "Unable to process trip."
        );
      }

      setTripData(data);

      setMessage(data.message);
      setMessageType("success");

    } catch (error) {

      setMessage(
        error.message ||
        "Unable to connect to Trip API."
      );

      setMessageType("error");

    } finally {

      setLoading(false);
    }
  };

  // ==============================
  // RESET
  // ==============================

  const handleReset = () => {

    setBookingId("");
    setTripData(null);
    setMessage("");
    setMessageType("");
  };

  return (

    <div className="trip-page">

      {/* Background Overlay */}

      <div className="trip-overlay"></div>

      <div className="trip-container">

        {/* ==============================
            HEADER
        ============================== */}

        <div className="trip-header">

          <div className="trip-header-icon">
            🚤
          </div>

          <div>

            <div className="trip-label">
              VANGA SUTHALAM
            </div>

            <h1>
              Trip Management
            </h1>

            <p>
              Start and complete customer trips
              after payment and safety verification.
            </p>

          </div>

        </div>


        {/* ==============================
            MAIN CARD
        ============================== */}

        <div className="trip-card">

          {/* Booking Information */}

          <div className="trip-section-title">

            <div className="section-number">
              01
            </div>

            <div>
              <h2>
                Trip Information
              </h2>

              <p>
                Enter the booking ID to manage the trip.
              </p>
            </div>

          </div>


          <div className="booking-input-area">

            <div className="trip-input-group">

              <label>
                Booking ID
              </label>

              <input
                type="number"
                min="1"
                placeholder="Enter booking ID"
                value={bookingId}
                onChange={(event) =>
                  setBookingId(event.target.value)
                }
              />

            </div>

          </div>


          {/* ==============================
              TRIP ACTIONS
          ============================== */}

          <div className="trip-section-title action-title">

            <div className="section-number">
              02
            </div>

            <div>
              <h2>
                Trip Controls
              </h2>

              <p>
                Manage the customer's current trip.
              </p>
            </div>

          </div>


          <div className="trip-actions">

            {/* START */}

            <div className="action-card start-card">

              <div className="action-icon">
                ▶
              </div>

              <div className="action-content">

                <h3>
                  Start Trip
                </h3>

                <p>
                  Start the trip after payment
                  and safety approval.
                </p>

                <button
                  type="button"
                  className="start-button"
                  onClick={() =>
                    handleTripAction("START")
                  }
                  disabled={loading}
                >
                  {loading
                    ? "Processing..."
                    : "Start Trip"}
                </button>

              </div>

            </div>


            {/* COMPLETE */}

            <div className="action-card complete-card">

              <div className="action-icon">
                ✓
              </div>

              <div className="action-content">

                <h3>
                  Complete Trip
                </h3>

                <p>
                  Complete the trip after the
                  customer reaches the destination.
                </p>

                <button
                  type="button"
                  className="complete-button"
                  onClick={() =>
                    handleTripAction("COMPLETE")
                  }
                  disabled={loading}
                >
                  {loading
                    ? "Processing..."
                    : "Complete Trip"}
                </button>

              </div>

            </div>

          </div>


          {/* ==============================
              MESSAGE
          ============================== */}

          {message && (

            <div
              className={
                messageType === "success"
                  ? "trip-message success"
                  : "trip-message error"
              }
            >

              <span>
                {messageType === "success"
                  ? "✓"
                  : "⚠"}
              </span>

              <p>
                {message}
              </p>

            </div>

          )}


          {/* ==============================
              RESULT
          ============================== */}

          {tripData && (

            <div className="trip-result">

              <div className="result-heading">
                <span>03</span>

                <div>
                  <h2>
                    Trip Status
                  </h2>

                  <p>
                    Latest trip operation result.
                  </p>
                </div>
              </div>


              <div className="result-grid">

                <div className="result-box">

                  <small>
                    Booking ID
                  </small>

                  <strong>
                    #{tripData.bookingId}
                  </strong>

                </div>


                <div className="result-box">

                  <small>
                    Trip ID
                  </small>

                  <strong>
                    #{tripData.tripId}
                  </strong>

                </div>


                <div className="result-box status-box">

                  <small>
                    Trip Status
                  </small>

                  <strong>
                    {tripData.tripStatus}
                  </strong>

                </div>

              </div>

            </div>

          )}


          {/* ==============================
              RESET
          ============================== */}

          <div className="trip-footer-buttons">

            <button
              type="button"
              className="trip-reset-button"
              onClick={handleReset}
            >
              Reset
            </button>

          </div>

        </div>


        {/* ==============================
            SAFETY NOTICE
        ============================== */}

        <div className="trip-notice">

          <div className="notice-icon">
            🛡️
          </div>

          <div>

            <strong>
              Trip Safety Requirement
            </strong>

            <p>
              A trip can only be started after
              successful payment confirmation and
              approved safety verification.
            </p>

          </div>

        </div>


      </div>

    </div>
  );
}

export default TripManagement;