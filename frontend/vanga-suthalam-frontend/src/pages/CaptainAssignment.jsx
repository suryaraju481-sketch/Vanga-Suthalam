import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import "./CaptainAssignment.css";

const API_BASE = "http://localhost:8090/VangaSuthalam1";

function CaptainAssignment() {
  const location = useLocation();
  const navigate = useNavigate();

  /* =========================================================
     DATA FROM PAYMENT SUCCESS / PREVIOUS PAGE
     ========================================================= */

  const incomingBooking = location.state?.booking || {};
  const incomingCustomer = location.state?.customer || {};
  const incomingPayment = location.state?.payment || {};

  /* =========================================================
     STATE
     ========================================================= */

  const [boats, setBoats] = useState([]);
  const [selectedBoat, setSelectedBoat] = useState(null);

  const [bookingId, setBookingId] = useState(
    incomingBooking.bookingId ||
      incomingBooking.booking_id ||
      incomingPayment.bookingId ||
      incomingPayment.booking_id ||
      localStorage.getItem("activeBookingId") ||
      ""
  );

  const [loading, setLoading] = useState(true);
  const [assigning, setAssigning] = useState(false);

  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  /* =========================================================
     LOAD AVAILABLE BOATS
     ========================================================= */

  useEffect(() => {
    loadAvailableBoats();
  }, []);

  const loadAvailableBoats = async () => {
    try {
      setLoading(true);
      setError("");

      const response = await fetch(
        `${API_BASE}/api/available-boats`
      );

      const data = await response.json();

      if (!response.ok || !data.success) {
        throw new Error(
          data.message ||
            "Unable to load available boats."
        );
      }

      setBoats(data.boats || []);
    } catch (err) {
      console.error("Available boats error:", err);

      setError(
        err.message ||
          "Unable to connect to Available Boats API."
      );
    } finally {
      setLoading(false);
    }
  };

  /* =========================================================
     SELECT BOAT
     ========================================================= */

  const handleSelectBoat = (boat) => {
    setSelectedBoat(boat);

    setMessage("");
    setError("");
  };

  /* =========================================================
     ASSIGN CAPTAIN + BOAT
     ========================================================= */

  const handleAssignBoat = async () => {
    setMessage("");
    setError("");

    if (!bookingId || String(bookingId).trim() === "") {
      setError("Booking ID is required.");
      return;
    }

    if (!selectedBoat) {
      setError("Please select a boat first.");
      return;
    }

    try {
      setAssigning(true);

      const formData = new URLSearchParams();

      formData.append(
        "bookingId",
        String(bookingId)
      );

      formData.append(
        "boatId",
        String(
          selectedBoat.boatId ||
            selectedBoat.boat_id
        )
      );

      const response = await fetch(
        `${API_BASE}/api/assign-boat`,
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
            "Captain and boat assignment failed."
        );
      }

      /* =====================================================
         GET ASSIGNED INFORMATION
         ===================================================== */

      const finalBookingId =
        data.bookingId ||
        data.booking_id ||
        bookingId;

      const finalTripId =
        data.tripId ||
        data.trip_id ||
        "";

      const finalCaptainId =
        data.captainId ||
        data.captain_id ||
        selectedBoat.captainId ||
        selectedBoat.captain_id ||
        "";

      const finalCaptainName =
        data.captainName ||
        data.captain_name ||
        selectedBoat.captainName ||
        selectedBoat.captain_name ||
        "Not available";

      const finalBoatId =
        data.boatId ||
        data.boat_id ||
        selectedBoat.boatId ||
        selectedBoat.boat_id ||
        "";

      const finalBoatName =
        data.boatName ||
        data.boat_name ||
        selectedBoat.boatName ||
        selectedBoat.boat_name ||
        "Not available";

      const finalBoatNumber =
        data.boatNumber ||
        data.boat_number ||
        selectedBoat.boatNumber ||
        selectedBoat.boat_number ||
        "Not available";

      const finalBoatCapacity =
        data.capacity ||
        data.boatCapacity ||
        data.boat_capacity ||
        selectedBoat.capacity ||
        selectedBoat.boatCapacity ||
        selectedBoat.boat_capacity ||
        "";

      const finalTripStatus =
        data.tripStatus ||
        data.trip_status ||
        "BOOKED";

      /* =====================================================
         SAVE ACTIVE BOOKING / TRIP
         ===================================================== */

      localStorage.setItem(
        "activeBookingId",
        String(finalBookingId)
      );

      if (finalTripId) {
        localStorage.setItem(
          "activeTripId",
          String(finalTripId)
        );
      }

      /* =====================================================
         ASSIGNMENT OBJECT
         ===================================================== */

      const assignment = {
        bookingId: finalBookingId,

        tripId: finalTripId,

        captainId: finalCaptainId,

        captainName: finalCaptainName,

        boatId: finalBoatId,

        boatName: finalBoatName,

        boatNumber: finalBoatNumber,

        capacity: finalBoatCapacity,

        tripStatus: finalTripStatus,

        passengers:
          incomingBooking.numberOfPeople ||
          incomingBooking.number_of_people ||
          incomingBooking.people ||
          incomingBooking.numberOfPersons ||
          "",
      };

      /* =====================================================
         SUCCESS MESSAGE
         ===================================================== */

      setMessage(
        `Captain ${finalCaptainName} and ${finalBoatName} assigned successfully.`
      );

      /* =====================================================
         REMOVE ASSIGNED BOAT FROM AVAILABLE LIST
         ===================================================== */

      setBoats((currentBoats) =>
        currentBoats.filter((boat) => {
          const currentBoatId =
            boat.boatId || boat.boat_id;

          return (
            String(currentBoatId) !==
            String(finalBoatId)
          );
        })
      );

      setSelectedBoat(null);

      /* =====================================================
         REDIRECT TO SAFETY VERIFICATION
         ===================================================== */

      setTimeout(() => {
        navigate(
          "/safety-verification",
          {
            state: {
              booking: {
                ...incomingBooking,

                bookingId: finalBookingId,
              },

              customer: incomingCustomer,

              payment: incomingPayment,

              assignment: assignment,

              trip: {
                tripId: finalTripId,

                bookingId: finalBookingId,

                captainId: finalCaptainId,

                captainName: finalCaptainName,

                boatId: finalBoatId,

                boatName: finalBoatName,

                boatNumber: finalBoatNumber,

                capacity: finalBoatCapacity,

                tripStatus: finalTripStatus,
              },
            },
          }
        );
      }, 1000);
    } catch (err) {
      console.error(
        "Captain assignment error:",
        err
      );

      setError(
        err.message ||
          "Unable to assign captain and boat."
      );
    } finally {
      setAssigning(false);
    }
  };

  /* =========================================================
     BACK TO PAYMENT SUCCESS
     ========================================================= */

  const handleBack = () => {
    navigate(
      "/payment-success",
      {
        state: {
          booking: incomingBooking,

          customer: incomingCustomer,

          payment: incomingPayment,
        },
      }
    );
  };

  /* =========================================================
     RENDER
     ========================================================= */

  return (
    <div className="assignment-page">

      <div className="assignment-overlay"></div>

      <div className="assignment-container">

        {/* =================================================
            HEADER
        ================================================= */}

        <div className="assignment-header">

          <div className="assignment-header-left">

            <div className="assignment-icon">
              🚤
            </div>

            <div>
              <p className="assignment-small-title">
                VANGA SUTHALAM
              </p>

              <h1>
                Captain & Boat Assignment
              </h1>

              <p>
                Assign an available captain and boat
                to your confirmed booking.
              </p>
            </div>

          </div>

          <button
            type="button"
            className="assignment-back-btn"
            onClick={handleBack}
          >
            ← Back
          </button>

        </div>

        {/* =================================================
            BOOKING INFORMATION
        ================================================= */}

        <div className="booking-panel">

          <div className="panel-title">

            <div className="panel-icon">
              📋
            </div>

            <div>
              <h2>
                Booking Information
              </h2>

              <p>
                Confirm the booking before assigning
                a captain and boat.
              </p>
            </div>

          </div>

          <div className="booking-input-row">

            <div className="assignment-input-group">

              <label>
                Booking ID
              </label>

              <input
                type="text"
                value={bookingId}
                onChange={(event) =>
                  setBookingId(
                    event.target.value
                  )
                }
                placeholder="Enter Booking ID"
              />

            </div>

            <div className="booking-summary-mini">

              <span>
                Payment
              </span>

              <strong>
                {incomingPayment.paymentStatus ||
                  incomingPayment.payment_status ||
                  "CONFIRMED"}
              </strong>

            </div>

          </div>

        </div>

        {/* =================================================
            MESSAGE
        ================================================= */}

        {message && (
          <div className="assignment-success-message">
            <span>✓</span>
            <p>{message}</p>
          </div>
        )}

        {error && (
          <div className="assignment-error-message">
            <span>!</span>
            <p>{error}</p>
          </div>
        )}

        {/* =================================================
            AVAILABLE BOATS
        ================================================= */}

        <div className="boats-section">

          <div className="section-header">

            <div>
              <h2>
                Available Boats
              </h2>

              <p>
                Select a boat to assign its captain
                to this booking.
              </p>
            </div>

            <div className="available-count">
              {boats.length} Available
            </div>

          </div>

          {loading ? (
            <div className="assignment-loading">

              <div className="assignment-spinner"></div>

              <p>
                Loading available boats...
              </p>

            </div>
          ) : boats.length === 0 ? (
            <div className="assignment-empty">

              <div>
                🚤
              </div>

              <h3>
                No Available Boats
              </h3>

              <p>
                There are currently no boats available
                for assignment.
              </p>

              <button
                type="button"
                onClick={loadAvailableBoats}
              >
                Refresh
              </button>

            </div>
          ) : (
            <div className="boats-grid">

              {boats.map((boat) => {

                const currentBoatId =
                  boat.boatId ||
                  boat.boat_id;

                const currentBoatName =
                  boat.boatName ||
                  boat.boat_name ||
                  "Boat";

                const currentBoatNumber =
                  boat.boatNumber ||
                  boat.boat_number ||
                  "Not available";

                const currentCaptainId =
                  boat.captainId ||
                  boat.captain_id ||
                  "";

                const currentCaptainName =
                  boat.captainName ||
                  boat.captain_name ||
                  "Not available";

                const currentCapacity =
                  boat.capacity ||
                  boat.boatCapacity ||
                  boat.boat_capacity ||
                  "";

                const currentBoatType =
                  boat.boatType ||
                  boat.boat_type ||
                  "Boat";

                const isSelected =
                  selectedBoat &&
                  String(
                    selectedBoat.boatId ||
                      selectedBoat.boat_id
                  ) ===
                    String(currentBoatId);

                return (
                  <div
                    key={currentBoatId}
                    className={
                      isSelected
                        ? "boat-card selected"
                        : "boat-card"
                    }
                  >

                    <div className="boat-image-area">

                      <img
                        src="/image.png"
                        alt="Vanga Suthalam Boat"
                      />

                      <div className="boat-type-badge">
                        {currentBoatType}
                      </div>

                      {isSelected && (
                        <div className="selected-badge">
                          ✓ SELECTED
                        </div>
                      )}

                    </div>

                    <div className="boat-card-body">

                      <h3>
                        {currentBoatName}
                      </h3>

                      <p className="boat-number">
                        {currentBoatNumber}
                      </p>

                      <div className="boat-details">

                        <div>
                          <span>
                            Captain
                          </span>

                          <strong>
                            {currentCaptainName}
                          </strong>
                        </div>

                        <div>
                          <span>
                            Captain ID
                          </span>

                          <strong>
                            {currentCaptainId ||
                              "N/A"}
                          </strong>
                        </div>

                        <div>
                          <span>
                            Capacity
                          </span>

                          <strong>
                            {currentCapacity
                              ? `${currentCapacity} persons`
                              : "N/A"}
                          </strong>
                        </div>

                      </div>

                      <button
                        type="button"
                        className={
                          isSelected
                            ? "select-boat-btn selected-btn"
                            : "select-boat-btn"
                        }
                        onClick={() =>
                          handleSelectBoat(
                            boat
                          )
                        }
                      >
                        {isSelected
                          ? "✓ Selected"
                          : "Select Boat"}
                      </button>

                    </div>

                  </div>
                );
              })}

            </div>
          )}

        </div>

        {/* =================================================
            SELECTED SUMMARY
        ================================================= */}

        {selectedBoat && (
          <div className="assignment-summary">

            <div className="summary-header">

              <div>
                <span className="summary-label">
                  SELECTED ASSIGNMENT
                </span>

                <h2>
                  Ready for Assignment
                </h2>
              </div>

              <span className="summary-status">
                READY
              </span>

            </div>

            <div className="summary-grid">

              <div>
                <span>
                  Booking
                </span>

                <strong>
                  #{bookingId}
                </strong>
              </div>

              <div>
                <span>
                  Captain
                </span>

                <strong>
                  {selectedBoat.captainName ||
                    selectedBoat.captain_name ||
                    "Not available"}
                </strong>
              </div>

              <div>
                <span>
                  Boat
                </span>

                <strong>
                  {selectedBoat.boatName ||
                    selectedBoat.boat_name ||
                    "Not available"}
                </strong>
              </div>

              <div>
                <span>
                  Capacity
                </span>

                <strong>
                  {selectedBoat.capacity
                    ? `${selectedBoat.capacity} persons`
                    : "Not available"}
                </strong>
              </div>

            </div>

            <button
              type="button"
              className="assign-btn"
              onClick={handleAssignBoat}
              disabled={assigning}
            >

              {assigning ? (
                <>
                  <span className="assignment-button-spinner"></span>
                  Assigning...
                </>
              ) : (
                <>
                  🚤 Assign Captain & Boat →
                </>
              )}

            </button>

            <p className="next-step-text">
              After assignment, you will continue
              automatically to Safety Verification.
            </p>

          </div>
        )}

        {/* =================================================
            FOOTER
        ================================================= */}

        <div className="assignment-footer">

          <span>
            VANGA SUTHALAM
          </span>

          <span>•</span>

          <span>
            Captain Assignment
          </span>

          <span>•</span>

          <span>
            Safety Verification Next
          </span>

        </div>

      </div>

    </div>
  );
}

export default CaptainAssignment;