import React, { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import "./Payment.css";

const PAYMENT_API_URL =
  "https://vanga-suthalam.onrender.com/api/payments";

function Payment() {
  const location = useLocation();
  const navigate = useNavigate();

  // ==========================================
  // GET COMPLETE BOOKING FROM BOOKING PAGE
  // ==========================================

  const bookingFromState =
    location.state?.booking || null;

  // ==========================================
  // FALLBACK BOOKING ID
  // ==========================================

  const storedBookingId =
    localStorage.getItem("bookingId");

  const bookingId =
    bookingFromState?.bookingId ||
    storedBookingId;

  // ==========================================
  // CUSTOMER INFORMATION
  // ==========================================

  const customer = {
    customerId:
      localStorage.getItem("customerId") || null,

    name:
      localStorage.getItem("customerName") || "",

    mobile:
      localStorage.getItem("customerMobile") || "",

    email:
      localStorage.getItem("customerEmail") || "",
  };

  // ==========================================
  // PAYMENT STATE
  // ==========================================

  const [paymentMethod, setPaymentMethod] =
    useState("UPI");

  const [transactionReference, setTransactionReference] =
    useState("");

  const [loading, setLoading] =
    useState(false);

  const [error, setError] =
    useState("");

  // ==========================================
  // IF BOOKING DOES NOT EXIST
  // ==========================================

  if (!bookingFromState && !bookingId) {
    return (
      <div className="payment-page">

        <div className="payment-missing-card">

          <div className="missing-icon">
            ⚠️
          </div>

          <h2>
            Booking Not Found
          </h2>

          <p>
            Please create a booking before
            continuing to payment.
          </p>

          <button
            className="back-booking-btn"
            onClick={() =>
              navigate("/packages")
            }
          >
            ← Go to Packages
          </button>

        </div>

      </div>
    );
  }

  // ==========================================
  // USE COMPLETE BOOKING OBJECT
  // ==========================================

  const booking = bookingFromState || {
    bookingId: bookingId,
    totalAmount: 0,
  };

  // ==========================================
  // PAYMENT AMOUNT
  // ==========================================

  const totalAmount =
    Number(booking.totalAmount || 0);

  // ==========================================
  // BOOKING DETAILS
  // ==========================================

  const packageName =
    booking.packageName ||
    booking.package ||
    booking.name ||
    "Sea Explorer";

  const destination =
    booking.destination ||
    booking.destinationName ||
    "Sea Adventure";

  const people =
    booking.people ||
    booking.numberOfPeople ||
    1;

  const bookingDate =
    booking.bookingDate ||
    booking.date ||
    "Not available";

  const startTime =
    booking.startTime ||
    booking.time ||
    "Not available";

  const approvalId =
    booking.approvalId ||
    null;

  const isIslandBooking =
    booking.isIslandBooking === true ||
    booking.isIslandPackage === true;

  // ==========================================
  // HANDLE PAYMENT
  // ==========================================

  const handlePayment = async (event) => {

    event.preventDefault();

    setError("");

    // ------------------------------------------
    // CHECK BOOKING ID
    // ------------------------------------------

    if (!bookingId) {

      setError(
        "Booking ID is missing. Please create the booking again."
      );

      return;
    }

    // ------------------------------------------
    // CHECK AMOUNT
    // ------------------------------------------

    if (
      !totalAmount ||
      totalAmount <= 0
    ) {

      setError(
        "Invalid payment amount."
      );

      return;
    }

    // ------------------------------------------
    // TRANSACTION REFERENCE
    // ------------------------------------------

    if (
      paymentMethod !== "Pay at Boat" &&
      !transactionReference.trim()
    ) {

      setError(
        "Please enter the transaction reference."
      );

      return;
    }

    try {

      setLoading(true);

      // ========================================
      // CREATE PAYMENT REQUEST
      // ========================================

      const body =
        new URLSearchParams();

      body.append(
        "bookingId",
        String(bookingId)
      );

      body.append(
        "amount",
        String(totalAmount)
      );

      body.append(
        "paymentMethod",
        paymentMethod
      );

      body.append(
        "transactionReference",
        transactionReference.trim()
      );

      // ========================================
      // SEND TO JAVA BACKEND
      // ========================================

      const response =
        await fetch(
          PAYMENT_API_URL,
          {
            method: "POST",

            headers: {
              "Content-Type":
                "application/x-www-form-urlencoded",
            },

            body:
              body.toString(),
          }
        );

      const data =
        await response.json();

      console.log(
        "Payment API response:",
        data
      );

      // ========================================
      // BACKEND ERROR
      // ========================================

      if (
        !response.ok ||
        !data.success
      ) {

        throw new Error(
          data.message ||
          "Payment failed."
        );
      }

      // ========================================
      // SAVE BOOKING ID
      // ========================================

      localStorage.setItem(
        "bookingId",
        String(bookingId)
      );

      // ========================================
      // SAVE PAYMENT ID
      // ========================================

      if (data.paymentId) {

        localStorage.setItem(
          "paymentId",
          String(data.paymentId)
        );
      }

      // ========================================
      // SAVE PAYMENT STATUS
      // ========================================

      localStorage.setItem(
        "paymentStatus",
        data.paymentStatus ||
        "CONFIRMED"
      );

      // ========================================
      // CREATE COMPLETE BOOKING OBJECT
      // ========================================
      //
      // THIS IS THE IMPORTANT FIX.
      //
      // We preserve the complete booking object
      // so PaymentSuccess and Feedback receive it.
      // ========================================

      const completedBooking = {

        ...booking,

        bookingId:
          Number(bookingId),

        packageName:
          packageName,

        destination:
          destination,

        destinationName:
          booking.destinationName ||
          destination,

        people:
          Number(people),

        numberOfPeople:
          Number(people),

        bookingDate:
          bookingDate,

        startTime:
          startTime,

        totalAmount:
          totalAmount,

        approvalId:
          approvalId,

        isIslandBooking:
          isIslandBooking,

        fishing:
          booking.fishing ??
          booking.fishingRequired ??
          false,

        fishingRequired:
          booking.fishingRequired ??
          booking.fishing ??
          false,

        food:
          booking.food ??
          booking.foodRequired ??
          false,

        foodRequired:
          booking.foodRequired ??
          booking.food ??
          false,
      };

      // ========================================
      // COMPLETE PAYMENT OBJECT
      // ========================================

      const payment = {

        paymentId:
          data.paymentId,

        bookingId:
          Number(bookingId),

        amount:
          totalAmount,

        paymentMethod:
          paymentMethod,

        transactionReference:
          transactionReference,

        paymentStatus:
          data.paymentStatus ||
          "CONFIRMED",
      };

      // ========================================
      // PAYMENT SUCCESS
      // ========================================

      navigate(
        "/payment-success",
        {
          state: {

            // COMPLETE BOOKING
            booking:
              completedBooking,

            // PAYMENT
            payment:
              payment,

            // CUSTOMER
            customer:
              customer,

            // PAYMENT DETAILS
            paymentMethod:
              paymentMethod,

            transactionId:
              transactionReference ||
              `VS-PAY-${data.paymentId}`,

          },
        }
      );

    } catch (err) {

      console.error(
        "Payment error:",
        err
      );

      setError(
        err.message ||
        "Unable to connect to the payment server."
      );

    } finally {

      setLoading(false);
    }
  };

  // ==========================================
  // RETURN UI
  // ==========================================

  return (
    <div className="payment-page">

      {/* ======================================
          HERO
      ======================================= */}

      <section className="payment-hero">

        <div className="payment-hero-overlay"></div>

        <div className="payment-hero-content">

          <span>
            🌊 VANGA SUTHALAM
          </span>

          <h1>
            Complete Your
            <strong>
              {" "}Sea Adventure
            </strong>
          </h1>

          <p>
            Secure your booking and get ready
            to explore the beautiful sea.
          </p>

        </div>

      </section>

      {/* ======================================
          PAYMENT CONTENT
      ======================================= */}

      <main className="payment-main">

        {/* ====================================
            LEFT SIDE
        ===================================== */}

        <section className="payment-form-card">

          <div className="payment-heading">

            <div className="payment-heading-icon">
              💳
            </div>

            <div>

              <span>
                SECURE PAYMENT
              </span>

              <h2>
                Choose Payment Method
              </h2>

              <p>
                Complete your payment to confirm
                your Vanga Suthalam booking.
              </p>

            </div>

          </div>

          {/* ERROR */}

          {error && (

            <div className="payment-error">

              <span>
                ⚠️
              </span>

              <p>
                {error}
              </p>

            </div>

          )}

          {/* ==================================
              PAYMENT METHODS
          =================================== */}

          <div className="payment-method-section">

            <label className="section-label">
              Payment Method
            </label>

            <div className="payment-methods">

              {/* UPI */}

              <button
                type="button"
                className={
                  paymentMethod === "UPI"
                    ? "method-card active"
                    : "method-card"
                }
                onClick={() => {
                  setPaymentMethod("UPI");
                  setError("");
                }}
              >

                <div className="method-icon">
                  📱
                </div>

                <div>

                  <strong>
                    UPI
                  </strong>

                  <small>
                    Google Pay / PhonePe / UPI
                  </small>

                </div>

                <span className="method-check">
                  {paymentMethod === "UPI"
                    ? "✓"
                    : ""}
                </span>

              </button>

              {/* CARD */}

              <button
                type="button"
                className={
                  paymentMethod === "Card"
                    ? "method-card active"
                    : "method-card"
                }
                onClick={() => {
                  setPaymentMethod("Card");
                  setError("");
                }}
              >

                <div className="method-icon">
                  💳
                </div>

                <div>

                  <strong>
                    Debit / Credit Card
                  </strong>

                  <small>
                    Visa / Mastercard / RuPay
                  </small>

                </div>

                <span className="method-check">
                  {paymentMethod === "Card"
                    ? "✓"
                    : ""}
                </span>

              </button>

              {/* NET BANKING */}

              <button
                type="button"
                className={
                  paymentMethod === "Net Banking"
                    ? "method-card active"
                    : "method-card"
                }
                onClick={() => {
                  setPaymentMethod(
                    "Net Banking"
                  );
                  setError("");
                }}
              >

                <div className="method-icon">
                  🏦
                </div>

                <div>

                  <strong>
                    Net Banking
                  </strong>

                  <small>
                    Pay directly from your bank
                  </small>

                </div>

                <span className="method-check">
                  {paymentMethod ===
                  "Net Banking"
                    ? "✓"
                    : ""}
                </span>

              </button>

              {/* PAY AT BOAT */}

              <button
                type="button"
                className={
                  paymentMethod ===
                  "Pay at Boat"
                    ? "method-card active"
                    : "method-card"
                }
                onClick={() => {
                  setPaymentMethod(
                    "Pay at Boat"
                  );
                  setError("");
                }}
              >

                <div className="method-icon">
                  🚤
                </div>

                <div>

                  <strong>
                    Pay at Boat
                  </strong>

                  <small>
                    Pay according to your booking arrangement
                  </small>

                </div>

                <span className="method-check">
                  {paymentMethod ===
                  "Pay at Boat"
                    ? "✓"
                    : ""}
                </span>

              </button>

            </div>

          </div>

          {/* ==================================
              TRANSACTION REFERENCE
          =================================== */}

          {paymentMethod !==
            "Pay at Boat" && (

            <div className="transaction-section">

              <label>
                Transaction Reference
              </label>

              <input
                type="text"
                value={
                  transactionReference
                }
                onChange={(event) => {

                  setTransactionReference(
                    event.target.value
                  );

                  setError("");

                }}
                placeholder={
                  paymentMethod === "UPI"
                    ? "Enter UPI transaction ID"
                    : "Enter transaction reference"
                }
              />

              <small>
                For development testing, enter
                any test transaction reference.
              </small>

            </div>

          )}

          {/* ==================================
              SECURITY
          =================================== */}

          <div className="payment-security">

            <div className="security-icon">
              🔒
            </div>

            <div>

              <strong>
                Secure Booking Process
              </strong>

              <p>
                Your booking information is sent
                securely to the Vanga Suthalam
                backend for payment processing.
              </p>

            </div>

          </div>

          {/* ==================================
              PAY BUTTON
          =================================== */}

          <button
            type="button"
            className="pay-button"
            onClick={handlePayment}
            disabled={loading}
          >

            {loading ? (

              <>
                <span className="button-spinner"></span>
                Processing Payment...
              </>

            ) : (

              <>
                🔒 Pay ₹
                {totalAmount.toLocaleString(
                  "en-IN"
                )}
              </>

            )}

          </button>

          <button
            type="button"
            className="back-button"
            onClick={() =>
              navigate(
                "/booking",
                {
                  state: {
                    package: booking
                      ? {
                          packageId:
                            booking.packageId,

                          name:
                            packageName,

                          priceValue:
                            booking.basePrice,

                          durationDays:
                            booking.durationDays,

                          durationNights:
                            booking.durationNights,

                          isIslandPackage:
                            isIslandBooking,

                          approvalId:
                            approvalId,

                          island:
                            booking.island,
                        }
                      : null,

                    destination:
                      booking?.destinationData ||
                      booking?.destination ||
                      null,

                    approvalId:
                      approvalId,

                    island:
                      booking?.island ||
                      null,
                  },
                }
              )
            }
          >
            ← Back to Booking
          </button>

        </section>

        {/* ====================================
            RIGHT SIDE SUMMARY
        ===================================== */}

        <aside className="payment-summary">

          <div className="payment-summary-image">

            <img
              src="https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=1400&q=90"
              alt="Beautiful sea"
            />

            <div className="image-overlay">

              <span>
                🌊 VANGA SUTHALAM
              </span>

              <strong>
                Your Sea Adventure Awaits
              </strong>

            </div>

          </div>

          <div className="summary-body">

            <div className="summary-top">

              <div>

                <span>
                  BOOKING SUMMARY
                </span>

                <h3>
                  Trip Details
                </h3>

              </div>

              <div className="booking-id">
                #{bookingId}
              </div>

            </div>

            <div className="summary-detail">

              <span>
                📦 Package
              </span>

              <strong>
                {packageName}
              </strong>

            </div>

            <div className="summary-detail">

              <span>
                🏝️ Destination
              </span>

              <strong>
                {destination}
              </strong>

            </div>

            <div className="summary-detail">

              <span>
                📅 Booking Date
              </span>

              <strong>
                {bookingDate}
              </strong>

            </div>

            <div className="summary-detail">

              <span>
                ⏰ Start Time
              </span>

              <strong>
                {startTime}
              </strong>

            </div>

            <div className="summary-detail">

              <span>
                👥 People
              </span>

              <strong>
                {people}
              </strong>

            </div>

            {isIslandBooking && (

              <div className="approval-status">

                <span>
                  🔒
                </span>

                <div>

                  <strong>
                    Island Approval Confirmed
                  </strong>

                  <small>
                    Approval ID: {approvalId}
                  </small>

                </div>

              </div>

            )}

            <div className="payment-price">

              <div>

                <span>
                  Package
                </span>

                <strong>
                  ₹
                  {(
                    Number(
                      booking.basePrice || 0
                    ) *
                    Number(people)
                  ).toLocaleString(
                    "en-IN"
                  )}
                </strong>

              </div>

              {booking.fishing && (

                <div>

                  <span>
                    🎣 Fishing
                  </span>

                  <strong>
                    ₹
                    {(
                      Number(
                        booking.fishingPrice ||
                        500
                      ) *
                      Number(people)
                    ).toLocaleString(
                      "en-IN"
                    )}
                  </strong>

                </div>

              )}

              {booking.food && (

                <div>

                  <span>
                    🍽️ Food
                  </span>

                  <strong>
                    ₹
                    {(
                      Number(
                        booking.foodPrice ||
                        500
                      ) *
                      Number(people)
                    ).toLocaleString(
                      "en-IN"
                    )}
                  </strong>

                </div>

              )}

            </div>

            <div className="payment-total">

              <div>

                <span>
                  TOTAL PAYABLE
                </span>

                <strong>
                  ₹
                  {totalAmount.toLocaleString(
                    "en-IN"
                  )}
                </strong>

              </div>

            </div>

            <div className="summary-footer">

              <span>
                ✓
              </span>

              <p>
                Booking ID #{bookingId}
                <br />
                Payment securely recorded
                in Vanga Suthalam.
              </p>

            </div>

          </div>

        </aside>

      </main>

      <footer className="payment-footer">

        <div className="footer-logo">
          🌊 VANGA SUTHALAM
        </div>

        <p>
          Sea and island exploration experiences
          around Ramanathapuram and Rameswaram.
        </p>

        <span>
          © 2026 Vanga Suthalam. All rights reserved.
        </span>

      </footer>

    </div>
  );
}

export default Payment;
