import React from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import "./PaymentSuccess.css";

function PaymentSuccess() {
  const location = useLocation();
  const navigate = useNavigate();

  const booking = location.state?.booking || {};
  const payment = location.state?.payment || {};
  const customer = location.state?.customer || {};

  // --------------------------------------------------
  // BOOKING ID
  // --------------------------------------------------
  const bookingId =
    booking?.bookingId ||
    booking?.booking_id ||
    payment?.bookingId ||
    payment?.booking_id ||
    localStorage.getItem("bookingId");

  // Save the latest booking ID
  if (bookingId) {
    localStorage.setItem("bookingId", bookingId);
  }

  // --------------------------------------------------
  // CUSTOMER ID
  // --------------------------------------------------
  const customerId =
    customer?.customerId ||
    customer?.customer_id ||
    booking?.customerId ||
    booking?.customer_id ||
    payment?.customerId ||
    payment?.customer_id ||
    localStorage.getItem("customerId");

  // --------------------------------------------------
  // PAYMENT DETAILS
  // --------------------------------------------------
  const paymentId =
    payment?.paymentId ||
    payment?.payment_id ||
    localStorage.getItem("paymentId") ||
    "N/A";

  const paymentMethod =
    payment?.paymentMethod ||
    payment?.payment_method ||
    "UPI";

  const transactionId =
    payment?.transactionReference ||
    payment?.transaction_reference ||
    payment?.transactionId ||
    localStorage.getItem("transactionId") ||
    "N/A";

  // --------------------------------------------------
  // AMOUNT
  // --------------------------------------------------
  const amount = Number(
    booking?.totalAmount ||
      booking?.total_amount ||
      payment?.amount ||
      0
  );

  // --------------------------------------------------
  // TRIP DETAILS
  // --------------------------------------------------
  const packageName =
    booking?.packageName ||
    booking?.package_name ||
    payment?.packageName ||
    payment?.package_name ||
    "Vanga Suthalam Package";

  const destination =
    booking?.destination ||
    booking?.destinationName ||
    booking?.destination_name ||
    payment?.destination ||
    payment?.destinationName ||
    "Sea Adventure";

  const people =
    booking?.numberOfPeople ||
    booking?.number_of_people ||
    booking?.people ||
    payment?.numberOfPeople ||
    payment?.number_of_people ||
    payment?.people ||
    1;

  const bookingDate =
    booking?.bookingDate ||
    booking?.booking_date ||
    payment?.bookingDate ||
    payment?.booking_date ||
    "Not available";

  const startTime =
    booking?.startTime ||
    booking?.start_time ||
    payment?.startTime ||
    payment?.start_time ||
    "Not available";

  const fishingRequired =
    booking?.fishingRequired ??
    booking?.fishing_required ??
    payment?.fishingRequired ??
    payment?.fishing_required ??
    false;

  const foodRequired =
    booking?.foodRequired ??
    booking?.food_required ??
    payment?.foodRequired ??
    payment?.food_required ??
    false;

  // --------------------------------------------------
  // CUSTOMER DETAILS
  // --------------------------------------------------
  const customerName =
    customer?.name ||
    customer?.customerName ||
    customer?.customer_name ||
    localStorage.getItem("customerName") ||
    "Customer";

  const customerEmail =
    customer?.email ||
    customer?.customerEmail ||
    customer?.customer_email ||
    localStorage.getItem("customerEmail") ||
    "Not available";

  const customerMobile =
    customer?.mobile ||
    customer?.phone ||
    customer?.mobileNumber ||
    customer?.customerMobile ||
    customer?.customer_mobile ||
    localStorage.getItem("customerMobile") ||
    "Not available";

  // --------------------------------------------------
  // ISLAND BOOKING
  // --------------------------------------------------
  const approvalId =
    booking?.approvalId ||
    booking?.approval_id ||
    payment?.approvalId ||
    payment?.approval_id ||
    null;

  const isIslandBooking =
    booking?.isIslandBooking ||
    booking?.islandBooking ||
    payment?.isIslandBooking ||
    false;

  // --------------------------------------------------
  // CONTINUE TO CAPTAIN ASSIGNMENT
  // --------------------------------------------------
  const handleCaptainAssignment = () => {
    if (!bookingId) {
      alert("Booking ID is missing. Please check the booking again.");
      return;
    }

    const assignmentBooking = {
      ...booking,

      bookingId: bookingId,

      customerId: customerId,

      customerName: customerName,

      customerEmail: customerEmail,

      customerMobile: customerMobile,

      packageName: packageName,

      destination: destination,

      numberOfPeople: people,

      people: people,

      bookingDate: bookingDate,

      startTime: startTime,

      totalAmount: amount,

      fishingRequired: fishingRequired,

      foodRequired: foodRequired,

      approvalId: approvalId,

      isIslandBooking: isIslandBooking,
    };

    // Keep important information for later pages
    localStorage.setItem("bookingId", bookingId);

    if (customerId) {
      localStorage.setItem("customerId", customerId);
    }

    localStorage.setItem("customerName", customerName);
    localStorage.setItem("customerEmail", customerEmail);
    localStorage.setItem("customerMobile", customerMobile);

    navigate("/captain-assignment", {
      state: {
        booking: assignmentBooking,
        customer: {
          customerId: customerId,
          name: customerName,
          email: customerEmail,
          mobile: customerMobile,
        },
        payment: payment,
      },
    });
  };

  return (
    <div className="payment-success-page">

      {/* ================= NAVBAR ================= */}

      <nav className="payment-success-navbar">

        <Link
          to="/"
          className="payment-success-logo"
        >
          🌊 Vanga Suthalam
        </Link>

        <Link
          to="/"
          className="payment-success-home"
        >
          ← Home
        </Link>

      </nav>


      {/* ================= HERO ================= */}

      <section className="payment-success-hero">

        <div className="payment-success-overlay"></div>

        <div className="payment-success-hero-content">

          <div className="success-check-circle">
            ✓
          </div>

          <span className="success-label">
            PAYMENT SUCCESSFUL
          </span>

          <h1>
            Your Trip Is Confirmed
          </h1>

          <p>
            Thank you for choosing Vanga Suthalam.
            Your payment has been successfully
            processed and your sea adventure is
            ready to begin.
          </p>

        </div>

      </section>


      {/* ================= MAIN ================= */}

      <main className="payment-success-container">


        {/* ================= CONFIRMATION ================= */}

        <section className="success-main-card">

          <div className="success-card-header">

            <div className="success-header-left">

              <div className="success-small-icon">
                ✓
              </div>

              <div>

                <span className="success-mini-label">
                  BOOKING CONFIRMED
                </span>

                <h2>
                  Payment completed successfully
                </h2>

                <p>
                  Your booking has been recorded in
                  the Vanga Suthalam system.
                </p>

              </div>

            </div>

            <div className="confirmed-badge">
              CONFIRMED
            </div>

          </div>


          {/* BOOKING ID */}

          <div className="booking-id-box">

            <div>

              <span>
                BOOKING ID
              </span>

              <strong>
                #{bookingId || "N/A"}
              </strong>

            </div>

            <div className="booking-id-right">
              🚤
            </div>

          </div>

        </section>


        {/* ================= PAYMENT DETAILS ================= */}

        <section className="success-section-card">

          <div className="success-section-title">

            <div className="section-icon">
              💳
            </div>

            <div>

              <h2>
                Payment Details
              </h2>

              <p>
                Your payment information
              </p>

            </div>

          </div>


          <div className="details-grid">

            <div className="detail-box amount-box">

              <span>
                AMOUNT PAID
              </span>

              <strong>
                ₹{amount.toLocaleString("en-IN")}
              </strong>

            </div>


            <div className="detail-box">

              <span>
                PAYMENT ID
              </span>

              <strong>
                {paymentId}
              </strong>

            </div>


            <div className="detail-box">

              <span>
                PAYMENT METHOD
              </span>

              <strong>
                {paymentMethod}
              </strong>

            </div>


            <div className="detail-box">

              <span>
                TRANSACTION ID
              </span>

              <strong className="transaction-text">
                {transactionId}
              </strong>

            </div>

          </div>

        </section>


        {/* ================= TRIP DETAILS ================= */}

        <section className="success-section-card">

          <div className="success-section-title">

            <div className="section-icon">
              🚤
            </div>

            <div>

              <h2>
                Your Trip Details
              </h2>

              <p>
                Information about your confirmed
                adventure
              </p>

            </div>

          </div>


          <div className="trip-details-grid">

            <div className="trip-detail-box">

              <div className="trip-icon">
                🏝️
              </div>

              <span>
                DESTINATION
              </span>

              <strong>
                {destination}
              </strong>

            </div>


            <div className="trip-detail-box">

              <div className="trip-icon">
                📦
              </div>

              <span>
                PACKAGE
              </span>

              <strong>
                {packageName}
              </strong>

            </div>


            <div className="trip-detail-box">

              <div className="trip-icon">
                📅
              </div>

              <span>
                TRIP DATE
              </span>

              <strong>
                {bookingDate}
              </strong>

            </div>


            <div className="trip-detail-box">

              <div className="trip-icon">
                ⏰
              </div>

              <span>
                START TIME
              </span>

              <strong>
                {startTime}
              </strong>

            </div>


            <div className="trip-detail-box">

              <div className="trip-icon">
                👥
              </div>

              <span>
                GUESTS
              </span>

              <strong>
                {people} Guest(s)
              </strong>

            </div>


            <div className="trip-detail-box">

              <div className="trip-icon">
                🎣
              </div>

              <span>
                FISHING
              </span>

              <strong>
                {fishingRequired
                  ? "Included"
                  : "Not selected"}
              </strong>

            </div>


            <div className="trip-detail-box">

              <div className="trip-icon">
                🍽️
              </div>

              <span>
                FOOD
              </span>

              <strong>
                {foodRequired
                  ? "Included"
                  : "Not selected"}
              </strong>

            </div>

          </div>

        </section>


        {/* ================= CUSTOMER ================= */}

        <section className="success-section-card">

          <div className="success-section-title">

            <div className="section-icon">
              👤
            </div>

            <div>

              <h2>
                Customer Information
              </h2>

              <p>
                Booking customer details
              </p>

            </div>

          </div>


          <div className="customer-grid">

            <div className="customer-box">

              <span>
                NAME
              </span>

              <strong>
                {customerName}
              </strong>

            </div>


            <div className="customer-box">

              <span>
                EMAIL
              </span>

              <strong>
                {customerEmail}
              </strong>

            </div>


            <div className="customer-box">

              <span>
                MOBILE
              </span>

              <strong>
                {customerMobile}
              </strong>

            </div>

          </div>

        </section>


        {/* ================= SAFETY ================= */}

        <section className="safety-notice">

          <div className="safety-notice-icon">
            🛟
          </div>

          <div>

            <h3>
              Important Trip Notice
            </h3>

            <p>
              Your payment is confirmed. The next
              step is captain and boat assignment.
              Safety verification must be completed
              before your trip can start.
            </p>

          </div>

        </section>


        {/* ================= NEXT STEP ================= */}

        <section className="feedback-cta">

          <div className="feedback-cta-left">

            <div className="feedback-cta-icon">
              🚤
            </div>

            <div>

              <h2>
                Next: Assign Your Boat
              </h2>

              <p>
                Select an available boat and captain
                for your confirmed booking.
              </p>

            </div>

          </div>


          <button
            type="button"
            className="feedback-button"
            onClick={handleCaptainAssignment}
          >
            Continue to Captain Assignment
            <span>→</span>
          </button>

        </section>


        {/* ================= ACTIONS ================= */}

        <div className="success-actions">

          <Link
            to="/"
            className="primary-action"
          >
            ← Back to Home
          </Link>

          <Link
            to="/destinations"
            className="secondary-action"
          >
            Explore More Destinations
          </Link>

        </div>


      </main>


      {/* ================= FOOTER ================= */}

      <footer className="payment-success-footer">

        <div className="footer-logo">
          🌊 Vanga Suthalam
        </div>

        <p>
          Ramanathapuram & Rameswaram
          Sea & Island Exploration
        </p>

        <span>
          © 2026 Vanga Suthalam • Explore Responsibly
        </span>

      </footer>

    </div>
  );
}

export default PaymentSuccess;