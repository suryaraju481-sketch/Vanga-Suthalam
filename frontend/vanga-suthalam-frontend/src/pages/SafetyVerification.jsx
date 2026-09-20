import React, { useMemo, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import "./SafetyVerification.css";

const API_BASE = "http://localhost:8090/VangaSuthalam1";

function SafetyVerification() {
  const navigate = useNavigate();
  const location = useLocation();

  /*
   * =========================================================
   * DATA RECEIVED FROM CAPTAIN ASSIGNMENT
   * =========================================================
   */

  const incomingBooking = location.state?.booking || {};
  const incomingCustomer = location.state?.customer || {};
  const incomingPayment = location.state?.payment || {};
  const incomingAssignment = location.state?.assignment || {};
  const incomingTrip = location.state?.trip || {};

  /*
   * =========================================================
   * HELPER
   * =========================================================
   */

  const getValue = (...values) => {
    for (const value of values) {
      if (
        value !== undefined &&
        value !== null &&
        String(value).trim() !== ""
      ) {
        return value;
      }
    }

    return "";
  };

  /*
   * =========================================================
   * BOOKING INFORMATION
   * =========================================================
   */

  const initialBookingId = getValue(
    incomingBooking.bookingId,
    incomingBooking.booking_id,
    incomingPayment.bookingId,
    incomingPayment.booking_id,
    incomingAssignment.bookingId,
    incomingAssignment.booking_id,
    incomingTrip.bookingId,
    incomingTrip.booking_id,
    localStorage.getItem("activeBookingId")
  );

  const initialTripId = getValue(
    incomingTrip.tripId,
    incomingTrip.trip_id,
    incomingAssignment.tripId,
    incomingAssignment.trip_id,
    localStorage.getItem("activeTripId")
  );

  const booking = useMemo(
    () => ({
      bookingId: initialBookingId,

      customerId: getValue(
        incomingBooking.customerId,
        incomingBooking.customer_id,
        incomingCustomer.customerId,
        incomingCustomer.customer_id
      ),

      customerName: getValue(
        incomingCustomer.name,
        incomingBooking.customerName,
        incomingBooking.customer_name,
        localStorage.getItem("customerName"),
        "Customer"
      ),

      customerEmail: getValue(
        incomingCustomer.email,
        incomingBooking.customerEmail,
        incomingBooking.customer_email,
        localStorage.getItem("customerEmail"),
        "Not available"
      ),

      customerMobile: getValue(
        incomingCustomer.mobile,
        incomingBooking.mobile,
        incomingBooking.customerMobile,
        incomingBooking.customer_mobile,
        localStorage.getItem("customerMobile"),
        "Not available"
      ),

      destinationName: getValue(
        incomingBooking.destinationName,
        incomingBooking.destination_name,
        incomingAssignment.destinationName,
        "Destination"
      ),

      packageName: getValue(
        incomingBooking.packageName,
        incomingBooking.package_name,
        incomingAssignment.packageName,
        "Trip Package"
      ),

      bookingDate: getValue(
        incomingBooking.bookingDate,
        incomingBooking.booking_date
      ),

      startTime: getValue(
        incomingBooking.startTime,
        incomingBooking.start_time
      ),

      passengers: getValue(
        incomingBooking.numberOfPeople,
        incomingBooking.number_of_people,
        incomingBooking.people,
        incomingBooking.numberOfPersons,
        incomingBooking.number_of_persons,
        incomingAssignment.passengers
      ),

      fishingRequired:
        incomingBooking.fishingRequired ??
        incomingBooking.fishing_required ??
        false,

      foodRequired:
        incomingBooking.foodRequired ??
        incomingBooking.food_required ??
        false,

      totalAmount: getValue(
        incomingBooking.totalAmount,
        incomingBooking.total_amount,
        incomingPayment.amount
      ),
    }),
    [
      initialBookingId,
      incomingBooking,
      incomingCustomer,
      incomingAssignment,
      incomingPayment,
    ]
  );

  /*
   * =========================================================
   * ASSIGNMENT INFORMATION
   * =========================================================
   */

  const captainId = getValue(
    incomingTrip.captainId,
    incomingTrip.captain_id,
    incomingAssignment.captainId,
    incomingAssignment.captain_id
  );

  const captainName = getValue(
    incomingTrip.captainName,
    incomingTrip.captain_name,
    incomingAssignment.captainName,
    incomingAssignment.captain_name,
    "Not available"
  );

  const boatId = getValue(
    incomingTrip.boatId,
    incomingTrip.boat_id,
    incomingAssignment.boatId,
    incomingAssignment.boat_id
  );

  const boatName = getValue(
    incomingTrip.boatName,
    incomingTrip.boat_name,
    incomingAssignment.boatName,
    incomingAssignment.boat_name,
    "Not available"
  );

  const boatNumber = getValue(
    incomingAssignment.boatNumber,
    incomingAssignment.boat_number,
    incomingTrip.boatNumber,
    incomingTrip.boat_number,
    "Not available"
  );

  const boatCapacity = getValue(
    incomingAssignment.capacity,
    incomingAssignment.boatCapacity,
    incomingAssignment.boat_capacity,
    incomingTrip.capacity,
    incomingTrip.boatCapacity,
    incomingTrip.boat_capacity,
    "Not available"
  );

  /*
   * =========================================================
   * STATE
   * =========================================================
   */

  const [bookingId, setBookingId] = useState(initialBookingId);

  const [checkedBy, setCheckedBy] = useState(
    getValue(
      localStorage.getItem("captainName"),
      captainName !== "Not available" ? captainName : "",
      "Captain"
    )
  );

  const [checks, setChecks] = useState({
    captainApproved: false,
    boatAvailable: false,
    passengerCapacityOk: false,
    lifeJacketsAvailable: false,
    emergencyEquipmentAvailable: false,
    communicationEquipmentAvailable: false,
    weatherClearance: false,
  });

  const [loading, setLoading] = useState(false);
  const [startingTrip, setStartingTrip] = useState(false);

  const [message, setMessage] = useState("");
  const [status, setStatus] = useState("");

  /*
   * =========================================================
   * CHECKLIST
   * =========================================================
   */

  const safetyItems = [
    {
      key: "captainApproved",
      icon: "👨‍✈️",
      title: "Captain Approved",
      description: "Assigned captain has been verified for this trip.",
    },
    {
      key: "boatAvailable",
      icon: "🚤",
      title: "Boat Available",
      description: "Assigned boat is available and ready for departure.",
    },
    {
      key: "passengerCapacityOk",
      icon: "👥",
      title: "Passenger Capacity",
      description: "Passenger count is within the boat's permitted capacity.",
    },
    {
      key: "lifeJacketsAvailable",
      icon: "🦺",
      title: "Life Jackets",
      description: "Required life jackets are available for passengers.",
    },
    {
      key: "emergencyEquipmentAvailable",
      icon: "🧰",
      title: "Emergency Equipment",
      description: "Emergency and rescue equipment has been checked.",
    },
    {
      key: "communicationEquipmentAvailable",
      icon: "📻",
      title: "Communication Equipment",
      description: "Communication equipment is available and operational.",
    },
    {
      key: "weatherClearance",
      icon: "🌤️",
      title: "Weather Clearance",
      description: "Current weather conditions have been cleared for travel.",
    },
  ];

  const completedChecks = Object.values(checks).filter(Boolean).length;

  const allChecksSelected = completedChecks === 7;

  const handleCheckChange = (key) => {
    setChecks((previous) => ({
      ...previous,
      [key]: !previous[key],
    }));

    setMessage("");
    setStatus("");
  };

  /*
   * =========================================================
   * VERIFY SAFETY
   * =========================================================
   */

  const handleVerifySafety = async (event) => {
    event.preventDefault();

    setMessage("");
    setStatus("");

    if (!bookingId) {
      setStatus("error");
      setMessage("Booking ID is missing. Please return to Captain Assignment.");
      return;
    }

    if (!checkedBy.trim()) {
      setStatus("error");
      setMessage("Please enter the person who performed the safety check.");
      return;
    }

    if (!allChecksSelected) {
      setStatus("rejected");
      setMessage(
        `Please complete all 7 safety checks. Currently completed: ${completedChecks}/7.`
      );
      return;
    }

    try {
      setLoading(true);

      const formData = new URLSearchParams();

      formData.append("bookingId", String(bookingId));
      formData.append(
        "captainApproved",
        String(checks.captainApproved)
      );
      formData.append(
        "boatAvailable",
        String(checks.boatAvailable)
      );
      formData.append(
        "passengerCapacityOk",
        String(checks.passengerCapacityOk)
      );
      formData.append(
        "lifeJacketsAvailable",
        String(checks.lifeJacketsAvailable)
      );
      formData.append(
        "emergencyEquipmentAvailable",
        String(checks.emergencyEquipmentAvailable)
      );
      formData.append(
        "communicationEquipmentAvailable",
        String(checks.communicationEquipmentAvailable)
      );
      formData.append(
        "weatherClearance",
        String(checks.weatherClearance)
      );
      formData.append("checkedBy", checkedBy.trim());

      const response = await fetch(
        `${API_BASE}/api/safety-verification`,
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
        setStatus("error");
        setMessage(
          data.message || "Safety verification failed."
        );
        return;
      }

      const safetyStatus = getValue(
        data.safetyStatus,
        data.safety_status,
        "APPROVED"
      );

      if (safetyStatus === "APPROVED") {
        setStatus("approved");
        setMessage(
          data.message ||
            "Safety verification completed successfully. The trip is ready to start."
        );

        localStorage.setItem(
          "safetyStatus",
          "APPROVED"
        );
        localStorage.setItem(
          "activeBookingId",
          String(bookingId)
        );
      } else {
        setStatus("rejected");
        setMessage(
          data.message ||
            "Safety verification was not approved."
        );
      }
    } catch (error) {
      console.error("Safety verification error:", error);

      setStatus("error");
      setMessage(
        "Unable to connect to Safety Verification API. Please check that Tomcat is running on port 8090."
      );
    } finally {
      setLoading(false);
    }
  };

  /*
   * =========================================================
   * START TRIP
   * =========================================================
   */

  const handleStartTrip = async () => {
    setMessage("");
    setStatus("");

    if (!bookingId) {
      setStatus("error");
      setMessage("Booking ID is missing.");
      return;
    }

    try {
      setStartingTrip(true);

      const formData = new URLSearchParams();
      formData.append("bookingId", String(bookingId));

      const response = await fetch(
        `${API_BASE}/api/start-trip`,
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
        setStatus("error");
        setMessage(
          data.message || "Unable to start the trip."
        );
        return;
      }

      const tripId = getValue(
        data.tripId,
        data.trip_id,
        initialTripId
      );

      const tripBookingId = getValue(
        data.bookingId,
        data.booking_id,
        bookingId
      );

      const tripData = {
        ...incomingTrip,

        tripId,
        bookingId: tripBookingId,

        captainId: getValue(
          data.captainId,
          data.captain_id,
          captainId
        ),

        captainName,

        boatId: getValue(
          data.boatId,
          data.boat_id,
          boatId
        ),

        boatName,

        boatNumber,

        boatCapacity,

        tripStatus: getValue(
          data.tripStatus,
          data.trip_status,
          "IN_PROGRESS"
        ),

        tripStart: getValue(
          data.tripStart,
          data.trip_start,
          new Date().toISOString()
        ),
      };

      localStorage.setItem(
        "activeTripId",
        String(tripId)
      );

      localStorage.setItem(
        "activeBookingId",
        String(tripBookingId)
      );

      localStorage.setItem(
        "tripStatus",
        "IN_PROGRESS"
      );

      setStatus("started");
      setMessage(
        data.message || "Trip started successfully."
      );

      /*
       * Move to Trip Status after a short delay
       */
      setTimeout(() => {
        navigate("/trip-status", {
          state: {
            trip: tripData,
            booking,
            customer: incomingCustomer,
            payment: incomingPayment,
            assignment: incomingAssignment,
          },
        });
      }, 900);
    } catch (error) {
      console.error("Start trip error:", error);

      setStatus("error");
      setMessage(
        "Unable to connect to Start Trip API."
      );
    } finally {
      setStartingTrip(false);
    }
  };

  /*
   * =========================================================
   * BACK
   * =========================================================
   */

  const handleBack = () => {
    navigate("/captain-assignment", {
      state: {
        booking: incomingBooking,
        customer: incomingCustomer,
        payment: incomingPayment,
        assignment: incomingAssignment,
        trip: incomingTrip,
      },
    });
  };

  /*
   * =========================================================
   * RENDER
   * =========================================================
   */

  return (
    <div className="safety-page">

      <div className="safety-background-overlay"></div>

      <div className="safety-container">

        {/* =================================================
            HEADER
        ================================================= */}

        <header className="safety-header">

          <div className="safety-brand">

            <div className="safety-brand-icon">
              🛡️
            </div>

            <div>
              <p className="safety-brand-small">
                VANGA SUTHALAM
              </p>

              <h1>
                Safety Verification
              </h1>

              <p className="safety-subtitle">
                Complete the pre-departure safety verification
                before starting the passenger trip.
              </p>
            </div>

          </div>

          <button
            type="button"
            className="safety-back-button"
            onClick={handleBack}
          >
            ← Assignment
          </button>

        </header>

        {/* =================================================
            ASSIGNED TRIP INFORMATION
        ================================================= */}

        <section className="assignment-info-card">

          <div className="assignment-info-header">

            <div className="assignment-title-area">

              <div className="assignment-info-icon">
                🚤
              </div>

              <div>
                <h2>
                  Assigned Trip Information
                </h2>

                <p>
                  Details received from Captain Assignment
                </p>
              </div>

            </div>

            <div className="assignment-status-badge">
              ASSIGNED
            </div>

          </div>

          <div className="assignment-info-grid">

            <div className="assignment-info-item">
              <span>Booking ID</span>
              <strong>
                {bookingId || "Not available"}
              </strong>
            </div>

            <div className="assignment-info-item">
              <span>Trip ID</span>
              <strong>
                {initialTripId || "Not available"}
              </strong>
            </div>

            <div className="assignment-info-item">
              <span>Captain</span>
              <strong>
                {captainName}
              </strong>
            </div>

            <div className="assignment-info-item">
              <span>Captain ID</span>
              <strong>
                {captainId || "Not available"}
              </strong>
            </div>

            <div className="assignment-info-item">
              <span>Boat</span>
              <strong>
                {boatName}
              </strong>
            </div>

            <div className="assignment-info-item">
              <span>Boat Number</span>
              <strong>
                {boatNumber}
              </strong>
            </div>

            <div className="assignment-info-item">
              <span>Boat ID</span>
              <strong>
                {boatId || "Not available"}
              </strong>
            </div>

            <div className="assignment-info-item">
              <span>Boat Capacity</span>
              <strong>
                {boatCapacity !== "Not available"
                  ? `${boatCapacity} passengers`
                  : "Not available"}
              </strong>
            </div>

            <div className="assignment-info-item">
              <span>Passengers</span>
              <strong>
                {booking.passengers || "Not available"}
              </strong>
            </div>

            <div className="assignment-info-item">
              <span>Destination</span>
              <strong>
                {booking.destinationName}
              </strong>
            </div>

            <div className="assignment-info-item">
              <span>Package</span>
              <strong>
                {booking.packageName}
              </strong>
            </div>

            <div className="assignment-info-item">
              <span>Payment</span>
              <strong className="payment-confirmed">
                {getValue(
                  incomingPayment.paymentStatus,
                  incomingPayment.payment_status,
                  "CONFIRMED"
                )}
              </strong>
            </div>

          </div>

        </section>

        {/* =================================================
            CUSTOMER INFORMATION
        ================================================= */}

        <section className="customer-info-card">

          <div className="section-heading">

            <div className="section-heading-icon">
              👤
            </div>

            <div>
              <h2>Passenger Information</h2>
              <p>Customer details for the assigned booking.</p>
            </div>

          </div>

          <div className="customer-grid">

            <div>
              <span>Name</span>
              <strong>{booking.customerName}</strong>
            </div>

            <div>
              <span>Email</span>
              <strong>{booking.customerEmail}</strong>
            </div>

            <div>
              <span>Mobile</span>
              <strong>{booking.customerMobile}</strong>
            </div>

            <div>
              <span>Passenger Count</span>
              <strong>
                {booking.passengers || "Not available"}
              </strong>
            </div>

          </div>

        </section>

        {/* =================================================
            SAFETY CHECK FORM
        ================================================= */}

        <form
          className="safety-form"
          onSubmit={handleVerifySafety}
        >

          <section className="checks-card">

            <div className="checks-card-header">

              <div className="section-heading">

                <div className="section-heading-icon shield">
                  🛡️
                </div>

                <div>
                  <h2>Safety Checklist</h2>

                  <p>
                    All 7 safety conditions must be verified
                    before the trip can start.
                  </p>
                </div>

              </div>

              <div
                className={
                  allChecksSelected
                    ? "check-progress complete"
                    : "check-progress"
                }
              >
                <strong>
                  {completedChecks}
                </strong>

                <span>/ 7</span>

              </div>

            </div>

            {/* =================================================
                CHECKED BY
            ================================================= */}

            <div className="checked-by-area">

              <label htmlFor="checkedBy">
                Checked By
              </label>

              <input
                id="checkedBy"
                type="text"
                value={checkedBy}
                onChange={(event) =>
                  setCheckedBy(event.target.value)
                }
                placeholder="Captain / Safety Officer"
              />

            </div>

            {/* =================================================
                SEVEN CHECKS
            ================================================= */}

            <div className="safety-checks-grid">

              {safetyItems.map((item) => {

                const selected = checks[item.key];

                return (
                  <button
                    type="button"
                    key={item.key}
                    className={
                      selected
                        ? "safety-check selected"
                        : "safety-check"
                    }
                    onClick={() =>
                      handleCheckChange(item.key)
                    }
                  >

                    <div className="check-icon">
                      {item.icon}
                    </div>

                    <div className="check-content">

                      <div className="check-title-row">

                        <h3>
                          {item.title}
                        </h3>

                        <span
                          className={
                            selected
                              ? "check-box checked"
                              : "check-box"
                          }
                        >
                          {selected ? "✓" : ""}
                        </span>

                      </div>

                      <p>
                        {item.description}
                      </p>

                    </div>

                  </button>
                );

              })}

            </div>

            {/* =================================================
                VERIFY BUTTON
            ================================================= */}

            <button
              type="submit"
              className="verify-safety-button"
              disabled={loading}
            >

              {loading ? (
                <>
                  <span className="button-spinner"></span>
                  Verifying Safety...
                </>
              ) : (
                <>
                  🛡️ Verify Safety
                </>
              )}

            </button>

          </section>

        </form>

        {/* =================================================
            RESULT MESSAGE
        ================================================= */}

        {message && (
          <section
            className={`safety-result-card ${status}`}
          >

            <div className="result-icon">

              {status === "approved"
                ? "✓"
                : status === "started"
                ? "🚤"
                : status === "rejected"
                ? "!"
                : "⚠"}

            </div>

            <div className="result-content">

              <h3>

                {status === "approved"
                  ? "Safety Approved"
                  : status === "started"
                  ? "Trip Started"
                  : status === "rejected"
                  ? "Safety Verification Incomplete"
                  : "Verification Error"}

              </h3>

              <p>
                {message}
              </p>

            </div>

          </section>
        )}

        {/* =================================================
            START TRIP
        ================================================= */}

        {status === "approved" && (
          <section className="start-trip-card">

            <div className="start-trip-content">

              <div className="start-trip-icon">
                🚤
              </div>

              <div>
                <span className="ready-label">
                  READY FOR DEPARTURE
                </span>

                <h2>
                  Safety verification completed
                </h2>

                <p>
                  Booking #{bookingId} has passed all
                  required safety checks.
                </p>
              </div>

            </div>

            <button
              type="button"
              className="start-trip-button"
              onClick={handleStartTrip}
              disabled={startingTrip}
            >

              {startingTrip ? (
                <>
                  <span className="button-spinner"></span>
                  Starting Trip...
                </>
              ) : (
                <>
                  Start Trip →
                </>
              )}

            </button>

          </section>
        )}

        {/* =================================================
            FOOTER
        ================================================= */}

        <footer className="safety-footer">
          <span>VANGA SUTHALAM</span>
          <span>•</span>
          <span>Safety First</span>
          <span>•</span>
          <span>Passenger Sea Exploration</span>
        </footer>

      </div>

    </div>
  );
}

export default SafetyVerification;