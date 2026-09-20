import React, { useEffect, useState } from "react";
import "./CaptainDashboard.css";

const API_BASE = "http://localhost:8090/VangaSuthalam1";

function CaptainDashboard() {
  const [captainId, setCaptainId] = useState("1");
  const [trips, setTrips] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const loadDashboard = async () => {
    if (!captainId.trim()) {
      setError("Captain ID is required.");
      return;
    }

    setLoading(true);
    setError("");

    try {
      const response = await fetch(
        `${API_BASE}/api/captain/dashboard?captainId=${captainId}`
      );

      const data = await response.json();

      if (!data.success) {
        throw new Error(data.message || "Unable to load dashboard.");
      }

      setTrips(Array.isArray(data.trips) ? data.trips : []);
    } catch (err) {
      console.error(err);
      setTrips([]);
      setError(err.message || "Unable to connect to server.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadDashboard();
  }, []);

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

    return time.substring(0, 5);
  };

  const paymentStatus = (status) => {
    if (!status) return "NOT PAID";
    return status.toUpperCase();
  };

  const safetyStatus = (status) => {
    if (!status) return "PENDING";
    return status.toUpperCase();
  };

  return (
    <div className="captain-dashboard">

      {/* Background Overlay */}
      <div className="dashboard-overlay"></div>

      <div className="dashboard-container">

        {/* Header */}
        <header className="dashboard-header">

          <div>
            <span className="dashboard-label">
              VANGA SUTHALAM
            </span>

            <h1>Captain Dashboard</h1>

            <p>
              Manage your assigned boats, customers and trips
              from one place.
            </p>
          </div>

          <div className="captain-id-box">
            <span>CAPTAIN ID</span>
            <strong>{captainId}</strong>
          </div>

        </header>

        {/* Captain ID Search */}
        <section className="dashboard-control">

          <div className="control-content">

            <div>
              <h2>Captain Access</h2>
              <p>
                Enter your captain ID to view assigned trips.
              </p>
            </div>

            <div className="captain-search">

              <input
                type="number"
                min="1"
                value={captainId}
                onChange={(e) => setCaptainId(e.target.value)}
                placeholder="Enter Captain ID"
              />

              <button
                onClick={loadDashboard}
                disabled={loading}
              >
                {loading ? "Loading..." : "View Trips"}
              </button>

            </div>

          </div>

        </section>

        {/* Error */}
        {error && (
          <div className="dashboard-message error-message">
            <span>⚠</span>
            <div>
              <strong>Unable to load dashboard</strong>
              <p>{error}</p>
            </div>
          </div>
        )}

        {/* Loading */}
        {loading && (
          <div className="loading-card">
            <div className="loading-spinner"></div>
            <p>Loading assigned trips...</p>
          </div>
        )}

        {/* No Trips */}
        {!loading && !error && trips.length === 0 && (
          <div className="empty-card">
            <div className="empty-icon">🚤</div>
            <h2>No Assigned Trips</h2>
            <p>
              There are currently no trips assigned to this captain.
            </p>
          </div>
        )}

        {/* Trips */}
        {!loading && trips.length > 0 && (
          <section className="trips-section">

            <div className="section-heading">
              <div>
                <span>ASSIGNED OPERATIONS</span>
                <h2>Your Trips</h2>
              </div>

              <div className="trip-count">
                {trips.length}{" "}
                {trips.length === 1 ? "Trip" : "Trips"}
              </div>
            </div>

            <div className="trip-grid">

              {trips.map((trip) => (

                <article
                  className="trip-card"
                  key={trip.tripId}
                >

                  {/* Trip Top */}
                  <div className="trip-top">

                    <div>
                      <span className="trip-number">
                        TRIP #{trip.tripId}
                      </span>

                      <h3>
                        {trip.destination?.name ||
                          "Destination unavailable"}
                      </h3>

                      <p className="location">
                        📍{" "}
                        {trip.destination?.location ||
                          "Location unavailable"}
                      </p>
                    </div>

                    <span
                      className={`status-badge ${(
                        trip.tripStatus || "BOOKED"
                      )
                        .toLowerCase()
                        .replace(/\s+/g, "-")}`}
                    >
                      {trip.tripStatus || "BOOKED"}
                    </span>

                  </div>

                  {/* Customer */}
                  <div className="information-block">

                    <div className="block-title">
                      👤 Customer Information
                    </div>

                    <div className="info-grid">

                      <div className="info-item">
                        <span>Name</span>
                        <strong>
                          {trip.customer?.name || "Not available"}
                        </strong>
                      </div>

                      <div className="info-item">
                        <span>Mobile</span>
                        <strong>
                          {trip.customer?.mobile || "Not available"}
                        </strong>
                      </div>

                      <div className="info-item">
                        <span>Email</span>
                        <strong className="small-text">
                          {trip.customer?.email || "Not available"}
                        </strong>
                      </div>

                      <div className="info-item">
                        <span>Passengers</span>
                        <strong>
                          {trip.booking?.passengers || 0}
                        </strong>
                      </div>

                    </div>

                  </div>

                  {/* Boat */}
                  <div className="information-block">

                    <div className="block-title">
                      🚤 Boat Information
                    </div>

                    <div className="boat-display">

                      <div className="boat-icon">
                        🚤
                      </div>

                      <div>
                        <strong>
                          {trip.boat?.name || "Boat unavailable"}
                        </strong>

                        <span>
                          Boat No:{" "}
                          {trip.boat?.number || "N/A"}
                        </span>

                        <span>
                          Capacity:{" "}
                          {trip.boat?.capacity || "N/A"} passengers
                        </span>
                      </div>

                    </div>

                  </div>

                  {/* Schedule */}
                  <div className="information-block">

                    <div className="block-title">
                      📅 Trip Schedule
                    </div>

                    <div className="schedule-grid">

                      <div>
                        <span>Date</span>
                        <strong>
                          {formatDate(trip.booking?.date)}
                        </strong>
                      </div>

                      <div>
                        <span>Start Time</span>
                        <strong>
                          {formatTime(trip.booking?.startTime)}
                        </strong>
                      </div>

                      <div>
                        <span>Package</span>
                        <strong>
                          {trip.package?.name || "N/A"}
                        </strong>
                      </div>

                      <div>
                        <span>Duration</span>
                        <strong>
                          {trip.package?.durationDays || 0} Day
                          {trip.package?.durationDays > 1 ? "s" : ""}
                        </strong>
                      </div>

                    </div>

                  </div>

                  {/* Requirements */}
                  <div className="requirements">

                    <div className="requirement-title">
                      TRIP REQUIREMENTS
                    </div>

                    <div className="requirement-list">

                      <span
                        className={
                          trip.booking?.fishingRequired
                            ? "active"
                            : ""
                        }
                      >
                        🎣 Fishing
                        {trip.booking?.fishingRequired
                          ? " Required"
                          : " Not Required"}
                      </span>

                      <span
                        className={
                          trip.booking?.foodRequired
                            ? "active"
                            : ""
                        }
                      >
                        🍱 Food
                        {trip.booking?.foodRequired
                          ? " Required"
                          : " Not Required"}
                      </span>

                    </div>

                  </div>

                  {/* Payment + Safety */}
                  <div className="status-row">

                    <div className="status-box">

                      <span>PAYMENT</span>

                      <strong
                        className={
                          paymentStatus(
                            trip.payment?.status
                          ) === "CONFIRMED"
                            ? "confirmed"
                            : "pending"
                        }
                      >
                        {paymentStatus(
                          trip.payment?.status
                        )}
                      </strong>

                    </div>

                    <div className="status-box">

                      <span>SAFETY</span>

                      <strong
                        className={
                          safetyStatus(
                            trip.safety?.status
                          ) === "APPROVED"
                            ? "confirmed"
                            : "pending"
                        }
                      >
                        {safetyStatus(
                          trip.safety?.status
                        )}
                      </strong>

                    </div>

                  </div>

                  {/* Amount */}
                  <div className="trip-footer">

                    <div>
                      <span>BOOKING ID</span>
                      <strong>
                        #{trip.bookingId}
                      </strong>
                    </div>

                    <div className="amount-box">
                      <span>BOOKING VALUE</span>
                      <strong>
                        ₹
                        {Number(
                          trip.booking?.totalAmount || 0
                        ).toLocaleString("en-IN")}
                      </strong>
                    </div>

                  </div>

                </article>

              ))}

            </div>

          </section>
        )}

      </div>
    </div>
  );
}

export default CaptainDashboard;