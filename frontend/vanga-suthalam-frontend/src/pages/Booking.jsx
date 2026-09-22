import React, { useEffect, useMemo, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import "./Booking.css";

const API_BASE = "https://vanga-suthalam.onrender.com/api/bookings";

function Booking() {
  const location = useLocation();
  const navigate = useNavigate();

  const passedPackage = location.state?.package || null;
  const passedDestination = location.state?.destination || null;
  const passedApproval = location.state?.approval || null;

  const [destinations, setDestinations] = useState([]);
  const [packages, setPackages] = useState([]);

  const [destinationId, setDestinationId] = useState(
    passedDestination?.destinationId || ""
  );

  const [packageId, setPackageId] = useState(
    passedPackage?.packageId || ""
  );

  const [bookingDate, setBookingDate] = useState("");
  const [startTime, setStartTime] = useState("");
  const [numberOfPeople, setNumberOfPeople] = useState(1);

  const [fishingRequired, setFishingRequired] = useState(false);
  const [foodRequired, setFoodRequired] = useState(false);

  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState("");

  const customerId = localStorage.getItem("customerId");

  // -------------------------------------------------
  // LOAD DESTINATIONS AND PACKAGES
  // -------------------------------------------------

  useEffect(() => {
    const loadData = async () => {
      try {
        setLoading(true);
        setError("");

        const [destinationResponse, packageResponse] =
          await Promise.all([
            fetch(`${API_BASE}/destinations`),
            fetch(`${API_BASE}/packages`),
          ]);

        if (!destinationResponse.ok) {
          throw new Error("Destination API is not available.");
        }

        if (!packageResponse.ok) {
          throw new Error("Package API is not available.");
        }

        const destinationData =
          await destinationResponse.json();

        const packageData =
          await packageResponse.json();

        if (!destinationData.success) {
          throw new Error(
            destinationData.message ||
              "Unable to load destinations."
          );
        }

        if (!packageData.success) {
          throw new Error(
            packageData.message ||
              "Unable to load packages."
          );
        }

        const destinationList =
          Array.isArray(destinationData.destinations)
            ? destinationData.destinations
            : [];

        const packageList =
          Array.isArray(packageData.packages)
            ? packageData.packages
            : [];

        setDestinations(destinationList);
        setPackages(packageList);

        // Keep passed destination/package selected
        if (passedDestination?.destinationId) {
          setDestinationId(
            String(passedDestination.destinationId)
          );
        }

        if (passedPackage?.packageId) {
          setPackageId(
            String(passedPackage.packageId)
          );
        }
      } catch (err) {
        console.error("Booking data error:", err);

        setError(
          err.message ||
            "Unable to connect to the booking server."
        );
      } finally {
        setLoading(false);
      }
    };

    loadData();
  }, []);

  // -------------------------------------------------
  // SELECTED DESTINATION
  // -------------------------------------------------

  const selectedDestination = useMemo(() => {
    return destinations.find(
      (item) =>
        String(item.destinationId) ===
        String(destinationId)
    );
  }, [destinations, destinationId]);

  // -------------------------------------------------
  // SELECTED PACKAGE
  // -------------------------------------------------

  const selectedPackage = useMemo(() => {
    return packages.find(
      (item) =>
        String(item.packageId) ===
        String(packageId)
    );
  }, [packages, packageId]);

  // -------------------------------------------------
  // ISLAND
  // -------------------------------------------------

  const isIslandExplorer =
    selectedDestination?.destinationType ===
    "Island Explorer";

  const hasApproval =
    Boolean(
      passedApproval?.approvalId ||
      localStorage.getItem("islandApprovalId")
    );

  // -------------------------------------------------
  // PRICE
  // -------------------------------------------------

  const basePrice = Number(
    selectedPackage?.basePricePerPerson || 0
  );

  const fishingPrice = fishingRequired
    ? 500
    : 0;

  const foodPrice = foodRequired
    ? 500
    : 0;

  const pricePerPerson =
    basePrice +
    fishingPrice +
    foodPrice;

  const totalAmount =
    pricePerPerson * numberOfPeople;

  // -------------------------------------------------
  // TODAY
  // -------------------------------------------------

  const today = new Date()
    .toISOString()
    .split("T")[0];

  // -------------------------------------------------
  // DESTINATION CHANGE
  // -------------------------------------------------

  const handleDestinationChange = (event) => {
    const value = event.target.value;

    setDestinationId(value);
    setError("");
  };

  // -------------------------------------------------
  // PACKAGE CHANGE
  // -------------------------------------------------

  const handlePackageChange = (event) => {
    const value = event.target.value;

    setPackageId(value);
    setError("");
  };

  // -------------------------------------------------
  // VALIDATE
  // -------------------------------------------------

  const validateForm = () => {
    if (!customerId) {
      setError(
        "Please login before creating a booking."
      );
      return false;
    }

    if (!destinationId) {
      setError(
        "Please select a destination."
      );
      return false;
    }

    if (!packageId) {
      setError(
        "Please select a trip package."
      );
      return false;
    }

    if (!bookingDate) {
      setError(
        "Please select your booking date."
      );
      return false;
    }

    if (!startTime) {
      setError(
        "Please select your starting time."
      );
      return false;
    }

    if (
      numberOfPeople < 1 ||
      numberOfPeople > 8
    ) {
      setError(
        "Passenger count must be between 1 and 8."
      );
      return false;
    }

    if (
      isIslandExplorer &&
      !hasApproval
    ) {
      setError(
        "Island approval is required before booking this destination."
      );
      return false;
    }

    return true;
  };

  // -------------------------------------------------
  // CREATE BOOKING
  // -------------------------------------------------

  const handleSubmit = async (event) => {
    event.preventDefault();

    setError("");

    if (!validateForm()) {
      return;
    }

    try {
      setSubmitting(true);

      const formData = new URLSearchParams();

      formData.append(
        "customerId",
        customerId
      );

      formData.append(
        "destinationId",
        destinationId
      );

      formData.append(
        "packageId",
        packageId
      );

      formData.append(
        "bookingDate",
        bookingDate
      );

      formData.append(
        "startTime",
        startTime
      );

      formData.append(
        "numberOfPeople",
        numberOfPeople
      );

      formData.append(
        "fishingRequired",
        fishingRequired
      );

      formData.append(
        "foodRequired",
        foodRequired
      );

      formData.append(
        "totalAmount",
        totalAmount.toFixed(2)
      );

      if (hasApproval) {
        const approvalId =
          passedApproval?.approvalId ||
          localStorage.getItem(
            "islandApprovalId"
          );

        if (approvalId) {
          formData.append(
            "approvalId",
            approvalId
          );
        }
      }

      const response = await fetch(
        `${API_BASE}/bookings`,
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
            "Booking creation failed."
        );
      }

      const bookingId =
        data.bookingId ||
        data.booking?.bookingId;

      if (!bookingId) {
        throw new Error(
          "Booking was created, but booking ID was not returned."
        );
      }

      // -------------------------------------------------
      // COMPLETE BOOKING OBJECT
      // -------------------------------------------------

      const booking = {
        bookingId: Number(bookingId),

        customerId: Number(customerId),

        destinationId:
          Number(destinationId),

        destination:
          selectedDestination?.destinationName ||
          "",

        destinationType:
          selectedDestination?.destinationType ||
          "",

        packageId:
          Number(packageId),

        packageName:
          selectedPackage?.packageName ||
          "",

        durationDays:
          Number(
            selectedPackage?.durationDays || 1
          ),

        durationNights:
          Number(
            selectedPackage?.durationNights || 0
          ),

        bookingDate,

        startTime,

        numberOfPeople:
          Number(numberOfPeople),

        fishingRequired,

        foodRequired,

        totalAmount:
          Number(totalAmount),

        bookingStatus:
          data.bookingStatus ||
          "PENDING",

        approvalId:
          passedApproval?.approvalId ||
          localStorage.getItem(
            "islandApprovalId"
          ) ||
          null,

        islandApprovalRequired:
          isIslandExplorer,
      };

      // Save booking
      localStorage.setItem(
        "latestBooking",
        JSON.stringify(booking)
      );

      // -------------------------------------------------
      // PAYMENT PAGE
      // -------------------------------------------------

      navigate("/payment", {
        state: {
          booking,
          customer: {
            customerId: Number(customerId),
          },
        },
      });
    } catch (err) {
      console.error(
        "Create booking error:",
        err
      );

      setError(
        err.message ||
          "Unable to create booking."
      );
    } finally {
      setSubmitting(false);
    }
  };

  // -------------------------------------------------
  // LOADING
  // -------------------------------------------------

  if (loading) {
    return (
      <div className="booking-page">
        <div className="booking-bg"></div>

        <div className="booking-loading-card">
          <div className="booking-loader"></div>

          <h2>
            Preparing Your Adventure
          </h2>

          <p>
            Loading destinations and packages...
          </p>
        </div>
      </div>
    );
  }

  // -------------------------------------------------
  // PAGE
  // -------------------------------------------------

  return (
    <div className="booking-page">

      <div className="booking-bg"></div>

      <div className="booking-content">

        {/* HEADER */}

        <header className="booking-topbar">

          <div>
            <span className="brand-label">
              VANGA SUTHALAM
            </span>

            <h1>
              Plan Your Sea Adventure
            </h1>

            <p>
              Build your perfect Ramanathapuram
              and Rameswaram experience.
            </p>
          </div>

          <button
            type="button"
            className="back-button"
            onClick={() =>
              navigate("/destinations")
            }
          >
            ← Destinations
          </button>

        </header>

        {/* ERROR */}

        {error && (
          <div className="booking-alert">

            <div className="alert-icon">
              !
            </div>

            <div>
              <strong>
                Booking Information
              </strong>

              <p>
                {error}
              </p>
            </div>

          </div>
        )}

        {/* MAIN */}

        <form
          className="booking-grid"
          onSubmit={handleSubmit}
        >

          {/* LEFT SIDE */}

          <section className="booking-form-card">

            {/* STEP 01 */}

            <div className="section-heading">

              <div className="step-badge">
                01
              </div>

              <div>
                <h2>
                  Trip Details
                </h2>

                <p>
                  Select your destination,
                  package and schedule.
                </p>
              </div>

            </div>

            {/* DESTINATION */}

            <div className="input-group">

              <label>
                Destination
              </label>

              <select
                value={destinationId}
                onChange={
                  handleDestinationChange
                }
              >

                <option value="">
                  Select destination
                </option>

                {destinations.map(
                  (destination) => (
                    <option
                      key={
                        destination.destinationId
                      }
                      value={
                        destination.destinationId
                      }
                    >
                      {
                        destination.destinationName
                      }
                      {" — "}
                      {
                        destination.destinationType
                      }
                    </option>
                  )
                )}

              </select>

            </div>

            {/* DESTINATION INFO */}

            {selectedDestination && (
              <div className="destination-info">

                <div className="destination-icon">
                  🌊
                </div>

                <div>

                  <strong>
                    {
                      selectedDestination.destinationName
                    }
                  </strong>

                  <span>
                    {
                      selectedDestination.location
                    }
                  </span>

                </div>

              </div>
            )}

            {/* ISLAND APPROVAL */}

            {isIslandExplorer && (
              <div className="island-alert">

                <div className="island-alert-icon">
                  ⚠
                </div>

                <div>

                  <strong>
                    Official Approval Required
                  </strong>

                  <p>
                    This Island Explorer destination
                    requires applicable official approval
                    before booking.
                  </p>

                  {hasApproval ? (
                    <span className="approval-confirmed">
                      ✓ Approval information available
                    </span>
                  ) : (
                    <button
                      type="button"
                      onClick={() =>
                        navigate(
                          "/island-approval",
                          {
                            state: {
                              destination:
                                selectedDestination,
                            },
                          }
                        )
                      }
                    >
                      Apply for Approval →
                    </button>
                  )}

                </div>

              </div>
            )}

            {/* PACKAGE */}

            <div className="input-group">

              <label>
                Trip Package
              </label>

              <select
                value={packageId}
                onChange={
                  handlePackageChange
                }
              >

                <option value="">
                  Select trip package
                </option>

                {packages.map((pkg) => (
                  <option
                    key={pkg.packageId}
                    value={pkg.packageId}
                  >
                    {pkg.packageName}
                    {" — ₹"}
                    {Number(
                      pkg.basePricePerPerson
                    ).toLocaleString(
                      "en-IN"
                    )}
                    /person
                  </option>
                ))}

              </select>

            </div>

            {/* PACKAGE CARD */}

            {selectedPackage && (
              <div className="package-card">

                <div className="package-top">

                  <div>
                    <span>
                      SELECTED PACKAGE
                    </span>

                    <h3>
                      {
                        selectedPackage.packageName
                      }
                    </h3>
                  </div>

                  <div className="package-price">
                    ₹
                    {Number(
                      selectedPackage.basePricePerPerson
                    ).toLocaleString(
                      "en-IN"
                    )}

                    <small>
                      /person
                    </small>
                  </div>

                </div>

                <p>
                  {
                    selectedPackage.description
                  }
                </p>

                <div className="package-details">

                  <span>
                    ⏱{" "}
                    {
                      selectedPackage.durationDays
                    }{" "}
                    Day
                    {selectedPackage.durationDays >
                    1
                      ? "s"
                      : ""}
                  </span>

                  <span>
                    🌙{" "}
                    {
                      selectedPackage.durationNights
                    }{" "}
                    Night
                    {selectedPackage.durationNights >
                    1
                      ? "s"
                      : ""}
                  </span>

                  {selectedPackage.fishingIncluded && (
                    <span>
                      🎣 Fishing
                    </span>
                  )}

                  {selectedPackage.foodIncluded && (
                    <span>
                      🍽 Food
                    </span>
                  )}

                </div>

              </div>
            )}

            {/* DATE + TIME */}

            <div className="two-inputs">

              <div className="input-group">

                <label>
                  Booking Date
                </label>

                <input
                  type="date"
                  min={today}
                  value={bookingDate}
                  onChange={(e) =>
                    setBookingDate(
                      e.target.value
                    )
                  }
                />

              </div>

              <div className="input-group">

                <label>
                  Start Time
                </label>

                <input
                  type="time"
                  value={startTime}
                  onChange={(e) =>
                    setStartTime(
                      e.target.value
                    )
                  }
                />

              </div>

            </div>

            {/* PASSENGERS */}

            <div className="input-group">

              <label>
                Number of People
              </label>

              <div className="passenger-control">

                <button
                  type="button"
                  onClick={() =>
                    setNumberOfPeople(
                      Math.max(
                        1,
                        numberOfPeople - 1
                      )
                    )
                  }
                >
                  −
                </button>

                <div className="passenger-count">

                  <strong>
                    {numberOfPeople}
                  </strong>

                  <span>
                    Passenger
                    {numberOfPeople > 1
                      ? "s"
                      : ""}
                  </span>

                </div>

                <button
                  type="button"
                  onClick={() =>
                    setNumberOfPeople(
                      Math.min(
                        8,
                        numberOfPeople + 1
                      )
                    )
                  }
                >
                  +
                </button>

              </div>

              <small>
                Maximum 8 passengers per booking.
              </small>

            </div>

            {/* EXTRA EXPERIENCES */}

            <div className="extras-section">

              <div className="section-heading">

                <div className="step-badge">
                  02
                </div>

                <div>
                  <h2>
                    Extra Experiences
                  </h2>

                  <p>
                    Add optional experiences
                    to your adventure.
                  </p>
                </div>

              </div>

              <div className="extra-options">

                {/* FISHING */}

                <label
                  className={
                    fishingRequired
                      ? "extra-card selected"
                      : "extra-card"
                  }
                >

                  <input
                    type="checkbox"
                    checked={
                      fishingRequired
                    }
                    onChange={(e) =>
                      setFishingRequired(
                        e.target.checked
                      )
                    }
                  />

                  <div className="extra-icon">
                    🎣
                  </div>

                  <div className="extra-text">

                    <strong>
                      Fishing Experience
                    </strong>

                    <span>
                      + ₹500 / person
                    </span>

                  </div>

                  <div className="extra-check">
                    ✓
                  </div>

                </label>

                {/* FOOD */}

                <label
                  className={
                    foodRequired
                      ? "extra-card selected"
                      : "extra-card"
                  }
                >

                  <input
                    type="checkbox"
                    checked={foodRequired}
                    onChange={(e) =>
                      setFoodRequired(
                        e.target.checked
                      )
                    }
                  />

                  <div className="extra-icon">
                    🍽
                  </div>

                  <div className="extra-text">

                    <strong>
                      Food Package
                    </strong>

                    <span>
                      + ₹500 / person
                    </span>

                  </div>

                  <div className="extra-check">
                    ✓
                  </div>

                </label>

              </div>

            </div>

          </section>

          {/* RIGHT SUMMARY */}

          <aside className="booking-summary">

            <div className="summary-label">
              BOOKING SUMMARY
            </div>

            <h2>
              Your Adventure
            </h2>

            {/* DESTINATION */}

            <div className="summary-destination">

              <div className="summary-icon">
                🌊
              </div>

              <div>

                <span>
                  DESTINATION
                </span>

                <strong>
                  {selectedDestination
                    ?.destinationName ||
                    "Not selected"}
                </strong>

              </div>

            </div>

            <div className="divider"></div>

            {/* DETAILS */}

            <div className="summary-item">

              <span>
                Package
              </span>

              <strong>
                {selectedPackage
                  ?.packageName ||
                  "Not selected"}
              </strong>

            </div>

            <div className="summary-item">

              <span>
                Date
              </span>

              <strong>
                {bookingDate ||
                  "Not selected"}
              </strong>

            </div>

            <div className="summary-item">

              <span>
                Start Time
              </span>

              <strong>
                {startTime ||
                  "Not selected"}
              </strong>

            </div>

            <div className="summary-item">

              <span>
                Passengers
              </span>

              <strong>
                {numberOfPeople}
              </strong>

            </div>

            <div className="divider"></div>

            {/* PRICE */}

            <div className="price-list">

              <div>
                <span>
                  Base package
                </span>

                <strong>
                  ₹
                  {(
                    basePrice *
                    numberOfPeople
                  ).toLocaleString(
                    "en-IN"
                  )}
                </strong>
              </div>

              {fishingRequired && (
                <div>
                  <span>
                    Fishing
                  </span>

                  <strong>
                    ₹
                    {(
                      500 *
                      numberOfPeople
                    ).toLocaleString(
                      "en-IN"
                    )}
                  </strong>
                </div>
              )}

              {foodRequired && (
                <div>
                  <span>
                    Food
                  </span>

                  <strong>
                    ₹
                    {(
                      500 *
                      numberOfPeople
                    ).toLocaleString(
                      "en-IN"
                    )}
                  </strong>
                </div>
              )}

            </div>

            {/* TOTAL */}

            <div className="total-card">

              <span>
                TOTAL AMOUNT
              </span>

              <strong>
                ₹
                {totalAmount.toLocaleString(
                  "en-IN"
                )}
              </strong>

            </div>

            {/* SUBMIT */}

            <button
              type="submit"
              className="payment-button"
              disabled={
                submitting ||
                (isIslandExplorer &&
                  !hasApproval)
              }
            >

              {submitting ? (
                <>
                  <span className="small-spinner"></span>
                  Creating Booking...
                </>
              ) : (
                <>
                  Continue to Payment
                  <span>→</span>
                </>
              )}

            </button>

            {isIslandExplorer &&
              !hasApproval && (
                <p className="approval-message">
                  Complete the required island
                  approval before continuing.
                </p>
              )}

            <div className="secure-message">
              🔒

              <span>
                Secure booking information
                processing.
              </span>
            </div>

          </aside>

        </form>

      </div>
    </div>
  );
}

export default Booking;
