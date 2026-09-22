import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./MyBookings.css";

const API_BASE = "https://vanga-suthalam.onrender.com";

function MyBookings() {
  const navigate = useNavigate();

  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const customerId = localStorage.getItem("customerId");

  useEffect(() => {
    loadBookings();
  }, []);

  const loadBookings = async () => {
    try {
      setLoading(true);
      setError("");

      if (!customerId) {
        setError("Please login to view your bookings.");
        setLoading(false);
        return;
      }

      const response = await fetch(
        `${API_BASE}/api/my-bookings?customerId=${customerId}`
      );

      const data = await response.json();

      if (!response.ok || !data.success) {
        throw new Error(data.message || "Unable to load bookings.");
      }

      setBookings(data.bookings || []);
    } catch (err) {
      console.error("My Bookings error:", err);
      setError(err.message || "Unable to load your bookings.");
    } finally {
      setLoading(false);
    }
  };

  const formatDate = (date) => {
    if (!date) return "Not available";

    const value = new Date(date);

    if (Number.isNaN(value.getTime())) {
      return date;
    }

    return value.toLocaleDateString("en-IN", {
      day: "2-digit",
      month: "short",
      year: "numeric",
    });
  };

  const formatTime = (time) => {
    if (!time) return "Not available";

    return String(time).substring(0, 5);
  };

  const formatAmount = (amount) => {
    const value = Number(amount || 0);

    return value.toLocaleString("en-IN", {
      style: "currency",
      currency: "INR",
      maximumFractionDigits: 0,
    });
  };

  const getStatusClass = (status) => {
    return String(status || "PENDING")
      .toLowerCase()
      .replace(/\s+/g, "-");
  };

  if (loading) {
    return (
      <div className="my-bookings-page">
        <div className="my-bookings-overlay"></div>

        <div className="my-bookings-loading">
          <div className="booking-spinner"></div>
          <h2>Loading Your Bookings</h2>
          <p>Please wait while we retrieve your booking history...</p>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="my-bookings-page">
        <div className="my-bookings-overlay"></div>

        <div className="my-bookings-error">
          <div className="error-icon">!</div>

          <h2>Unable to Load Bookings</h2>

          <p>{error}</p>

          <div className="error-actions">
            <button onClick={loadBookings}>Try Again</button>

            <button
              className="secondary-btn"
              onClick={() => navigate("/")}
            >
              Back Home
            </button>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="my-bookings-page">
      <div className="my-bookings-overlay"></div>

      <div className="my-bookings-container">

        {/* Header */}
        <header className="my-bookings-header">

          <div className="header-content">

            <div className="header-icon">
              🚤
            </div>

            <div>
              <div className="brand-label">
                VANGA SUTHALAM
              </div>

              <h1>My Bookings</h1>

              <p>
                View and manage your sea exploration journeys.
              </p>
            </div>

          </div>

          <button
            className="back-home-btn"
            onClick={() => navigate("/")}
          >
            ← Home
          </button>

        </header>

        {/* Summary */}
        <section className="booking-summary-bar">

          <div className="summary-stat">
            <span className="stat-icon">📋</span>

            <div>
              <strong>{bookings.length}</strong>
              <span>Total Bookings</span>
            </div>
          </div>

          <div className="summary-divider"></div>

          <div className="summary-stat">
            <span className="stat-icon">✓</span>

            <div>
              <strong>
                {
                  bookings.filter(
                    (booking) =>
                      String(booking.bookingStatus).toUpperCase() ===
                      "COMPLETED"
                  ).length
                }
              </strong>

              <span>Completed</span>
            </div>
          </div>

          <div className="summary-divider"></div>

          <div className="summary-stat">
            <span className="stat-icon">💳</span>

            <div>
              <strong>
                {
                  bookings.filter(
                    (booking) =>
                      String(booking.payment?.status).toUpperCase() ===
                      "CONFIRMED"
                  ).length
                }
              </strong>

              <span>Paid</span>
            </div>
          </div>

        </section>

        {/* Empty */}
        {bookings.length === 0 ? (
          <section className="empty-bookings">

            <div className="empty-icon">
              🌊
            </div>

            <h2>No Bookings Yet</h2>

            <p>
              You haven't created any trips yet.
              Start exploring Ramanathapuram and Rameswaram.
            </p>

            <button
              onClick={() => navigate("/destinations")}
            >
              Explore Destinations →
            </button>

          </section>
        ) : (

          <section className="bookings-list">

            {bookings.map((booking) => (

              <article
                className="booking-card"
                key={booking.bookingId}
              >

                {/* Card Header */}
                <div className="booking-card-header">

                  <div>
                    <span className="booking-label">
                      BOOKING
                    </span>

                    <h2>
                      #{booking.bookingId}
                    </h2>
                  </div>

                  <span
                    className={`booking-status ${getStatusClass(
                      booking.bookingStatus
                    )}`}
                  >
                    {booking.bookingStatus || "PENDING"}
                  </span>

                </div>

                {/* Main Information */}
                <div className="booking-main">

                  <div className="destination-block">

                    <span className="info-label">
                      DESTINATION
                    </span>

                    <h3>
                      {booking.destination?.name ||
                        "Destination unavailable"}
                    </h3>

                    <p>
                      📍 {booking.destination?.location ||
                        "Location unavailable"}
                    </p>

                    <span className="destination-type">
                      {booking.destination?.type ||
                        "Sea Exploration"}
                    </span>

                  </div>

                  <div className="package-block">

                    <span className="info-label">
                      PACKAGE
                    </span>

                    <h3>
                      {booking.package?.name ||
                        "Package unavailable"}
                    </h3>

                    <p>
                      {booking.package?.durationDays || 0} Day
                      {booking.package?.durationDays > 1
                        ? "s"
                        : ""}
                      {" • "}
                      {booking.package?.durationNights || 0} Night
                      {booking.package?.durationNights > 1
                        ? "s"
                        : ""}
                    </p>

                  </div>

                </div>

                {/* Booking Details */}
                <div className="details-grid">

                  <div className="detail-item">
                    <span>📅 Date</span>
                    <strong>
                      {formatDate(booking.bookingDate)}
                    </strong>
                  </div>

                  <div className="detail-item">
                    <span>⏰ Start Time</span>
                    <strong>
                      {formatTime(booking.startTime)}
                    </strong>
                  </div>

                  <div className="detail-item">
                    <span>👥 Passengers</span>
                    <strong>
                      {booking.numberOfPeople}
                    </strong>
                  </div>

                  <div className="detail-item">
                    <span>💰 Total Amount</span>
                    <strong className="amount">
                      {formatAmount(booking.totalAmount)}
                    </strong>
                  </div>

                </div>

                {/* Options */}
                <div className="options-row">

                  <span
                    className={
                      booking.fishingRequired
                        ? "option active"
                        : "option"
                    }
                  >
                    🎣 Fishing{" "}
                    {booking.fishingRequired ? "Included" : "No"}
                  </span>

                  <span
                    className={
                      booking.foodRequired
                        ? "option active"
                        : "option"
                    }
                  >
                    🍽️ Food{" "}
                    {booking.foodRequired ? "Included" : "No"}
                  </span>

                </div>

                {/* Captain / Boat / Trip */}
                <div className="assignment-section">

                  <div className="assignment-item">

                    <span className="assignment-icon">
                      👨‍✈️
                    </span>

                    <div>
                      <span>Captain</span>

                      <strong>
                        {booking.captain?.name ||
                          "Not assigned"}
                      </strong>

                      {booking.captain?.mobile && (
                        <small>
                          {booking.captain.mobile}
                        </small>
                      )}
                    </div>

                  </div>

                  <div className="assignment-item">

                    <span className="assignment-icon">
                      🚤
                    </span>

                    <div>
                      <span>Boat</span>

                      <strong>
                        {booking.boat?.boatName ||
                          "Not assigned"}
                      </strong>

                      {booking.boat?.boatNumber && (
                        <small>
                          {booking.boat.boatNumber}
                        </small>
                      )}
                    </div>

                  </div>

                  <div className="assignment-item">

                    <span className="assignment-icon">
                      🌊
                    </span>

                    <div>
                      <span>Trip Status</span>

                      <strong>
                        {booking.trip?.status ||
                          "Not started"}
                      </strong>
                    </div>

                  </div>

                  <div className="assignment-item">

                    <span className="assignment-icon">
                      💳
                    </span>

                    <div>
                      <span>Payment</span>

                      <strong>
                        {booking.payment?.status ||
                          "Pending"}
                      </strong>
                    </div>

                  </div>

                </div>

                {/* Footer */}
                <div className="booking-card-footer">

                  <div>

                    {booking.feedbackSubmitted ? (
                      <span className="feedback-complete">
                        ✓ Feedback Submitted
                      </span>
                    ) : (
                      <span className="feedback-pending">
                        Feedback not submitted
                      </span>
                    )}

                  </div>

                  <button
                    className="details-btn"
                    onClick={() =>
                      navigate("/trip-status", {
                        state: {
                          booking: booking,
                          trip: booking.trip,
                        },
                      })
                    }
                  >
                    View Trip →
                  </button>

                </div>

              </article>

            ))}

          </section>
        )}

        <footer className="my-bookings-footer">
          <span>VANGA SUTHALAM</span>
          <span>•</span>
          <span>Sea & Island Exploration</span>
          <span>•</span>
          <span>Ramanathapuram & Rameswaram</span>
        </footer>

      </div>
    </div>
  );
}

export default MyBookings;
