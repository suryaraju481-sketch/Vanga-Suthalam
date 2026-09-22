import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import "./IslandApproval.css";

const API_BASE = "https://vanga-suthalam.onrender.com";

function IslandApproval() {
  const location = useLocation();
  const navigate = useNavigate();

  const island = location.state?.island;

  const customerId = localStorage.getItem("customerId");

  const [formData, setFormData] = useState({
    certificateNumber: "",
    approvedBy: "",
    startDate: "",
    endDate: "",
    remarks: "",
  });

  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  if (!island) {
    return (
      <div className="approval-page">
        <div className="approval-error-card">
          <div className="approval-error-icon">⚠️</div>

          <h2>Island not selected</h2>

          <p>
            Please select an island from the Destinations page
            before applying for approval.
          </p>

          <button
            onClick={() => navigate("/destinations")}
            className="approval-back-btn"
          >
            Back to Destinations
          </button>
        </div>
      </div>
    );
  }

  const handleChange = (event) => {
    const { name, value } = event.target;

    setFormData((previous) => ({
      ...previous,
      [name]: value,
    }));

    setError("");
    setMessage("");
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    setError("");
    setMessage("");

    if (!customerId) {
      setError("Please login before applying for island approval.");
      return;
    }

    if (!formData.certificateNumber.trim()) {
      setError("Please enter the certificate number.");
      return;
    }

    if (!formData.approvedBy.trim()) {
      setError("Please enter the approving authority.");
      return;
    }

    if (!formData.startDate || !formData.endDate) {
      setError("Please select the approval dates.");
      return;
    }

    if (formData.endDate < formData.startDate) {
      setError("End date cannot be before start date.");
      return;
    }

    const start = new Date(formData.startDate);
    const end = new Date(formData.endDate);

    const difference =
      Math.floor(
        (end.getTime() - start.getTime()) /
          (1000 * 60 * 60 * 24)
      ) + 1;

    if (difference > 2) {
      setError(
        "Island approval cannot exceed 2 days."
      );
      return;
    }

    try {
      setLoading(true);

      const body = new URLSearchParams();

      body.append("customerId", customerId);
      body.append(
        "destinationId",
        island.destinationId
      );
      body.append(
        "certificateNumber",
        formData.certificateNumber.trim()
      );
      body.append(
        "approvedBy",
        formData.approvedBy.trim()
      );
      body.append(
        "startDate",
        formData.startDate
      );
      body.append(
        "endDate",
        formData.endDate
      );
      body.append(
        "remarks",
        formData.remarks.trim()
      );

      const response = await fetch(API_URL, {
        method: "POST",
        headers: {
          "Content-Type":
            "application/x-www-form-urlencoded",
        },
        body: body.toString(),
      });

      const data = await response.json();

      if (!response.ok || !data.success) {
        throw new Error(
          data.message ||
            "Unable to apply for island approval."
        );
      }

      setMessage(
        "Island approval confirmed successfully."
      );

      localStorage.setItem(
        "islandApprovalId",
        data.approvalId
      );

      localStorage.setItem(
        "islandDestinationId",
        data.destinationId
      );

      setTimeout(() => {
        navigate("/packages", {
          state: {
            islandPackage: true,
            approvalId: data.approvalId,
            island: island,
          },
        });
      }, 1200);

    } catch (err) {
      setError(
        err.message ||
          "Unable to connect to the Java backend."
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="approval-page">

      {/* NAVBAR */}

      <nav className="approval-navbar">

        <div
          className="approval-logo"
          onClick={() => navigate("/")}
        >
          🌊 VANGA SUTHALAM
        </div>

        <div className="approval-nav-links">

          <button onClick={() => navigate("/")}>
            Home
          </button>

          <button
            onClick={() =>
              navigate("/destinations")
            }
          >
            Destinations
          </button>

          <button
            onClick={() =>
              navigate("/packages")
            }
          >
            Packages
          </button>

          <button
            onClick={() =>
              navigate("/feedback")
            }
          >
            Feedback
          </button>

        </div>

        <div className="approval-nav-actions">

          <button
            className="approval-login-btn"
            onClick={() =>
              navigate("/login")
            }
          >
            Login
          </button>

          <button
            className="approval-register-btn"
            onClick={() =>
              navigate("/register")
            }
          >
            Register
          </button>

        </div>

      </nav>

      {/* HERO */}

      <section className="approval-hero">

        <div className="approval-hero-overlay"></div>

        <div className="approval-hero-content">

          <span>
            🏝️ ISLAND EXPLORER
          </span>

          <h1>
            Island Approval
          </h1>

          <p>
            Apply for approval before selecting
            an island exploration package.
          </p>

        </div>

      </section>

      {/* FLOW */}

      <section className="approval-flow-section">

        <div className="approval-flow">

          <div className="flow-item active">
            <span>1</span>
            <strong>Select Island</strong>
          </div>

          <div className="flow-line"></div>

          <div className="flow-item active">
            <span>2</span>
            <strong>Apply for Approval</strong>
          </div>

          <div className="flow-line"></div>

          <div className="flow-item">
            <span>3</span>
            <strong>Approval Confirmed</strong>
          </div>

          <div className="flow-line"></div>

          <div className="flow-item">
            <span>4</span>
            <strong>Select Package</strong>
          </div>

        </div>

      </section>

      {/* MAIN */}

      <main className="approval-main">

        {/* SELECTED ISLAND */}

        <div className="selected-island-card">

          <div className="selected-island-icon">
            🏝️
          </div>

          <div>

            <span>
              SELECTED ISLAND
            </span>

            <h2>
              {island.destinationName}
            </h2>

            <p>
              📍 {island.location}
            </p>

          </div>

          <div className="approval-required-badge">
            🔒 Approval Required
          </div>

        </div>

        {/* FORM */}

        <div className="approval-form-card">

          <div className="form-heading">

            <span>
              OFFICIAL APPROVAL DETAILS
            </span>

            <h2>
              Apply for Island Approval
            </h2>

            <p>
              Enter the required approval and
              certificate details for your selected
              island.
            </p>

          </div>

          {error && (
            <div className="approval-message error">
              ⚠️ {error}
            </div>
          )}

          {message && (
            <div className="approval-message success">
              ✅ {message}
            </div>
          )}

          <form onSubmit={handleSubmit}>

            <div className="form-grid">

              <div className="form-group">

                <label>
                  Certificate Number *
                </label>

                <input
                  type="text"
                  name="certificateNumber"
                  value={
                    formData.certificateNumber
                  }
                  onChange={handleChange}
                  placeholder="Example: CERT-001"
                />

              </div>

              <div className="form-group">

                <label>
                  Approved By *
                </label>

                <input
                  type="text"
                  name="approvedBy"
                  value={
                    formData.approvedBy
                  }
                  onChange={handleChange}
                  placeholder="Approving authority"
                />

              </div>

              <div className="form-group">

                <label>
                  Approval Start Date *
                </label>

                <input
                  type="date"
                  name="startDate"
                  value={
                    formData.startDate
                  }
                  onChange={handleChange}
                />

              </div>

              <div className="form-group">

                <label>
                  Approval End Date *
                </label>

                <input
                  type="date"
                  name="endDate"
                  value={
                    formData.endDate
                  }
                  onChange={handleChange}
                />

              </div>

              <div className="form-group full-width">

                <label>
                  Remarks
                </label>

                <textarea
                  name="remarks"
                  value={
                    formData.remarks
                  }
                  onChange={handleChange}
                  placeholder="Enter additional approval details..."
                  rows="4"
                ></textarea>

              </div>

            </div>

            <div className="approval-note">

              <span>ℹ️</span>

              <p>
                Island approval is limited to a
                maximum of <strong>2 days</strong>
                in this application flow.
              </p>

            </div>

            <div className="form-buttons">

              <button
                type="button"
                className="cancel-approval-btn"
                onClick={() =>
                  navigate("/destinations")
                }
              >
                ← Back
              </button>

              <button
                type="submit"
                className="submit-approval-btn"
                disabled={loading}
              >
                {loading
                  ? "Processing..."
                  : "✓ Confirm Approval"}
              </button>

            </div>

          </form>

        </div>

      </main>

      {/* FOOTER */}

      <footer className="approval-footer">

        <div className="approval-footer-logo">
          🌊 VANGA SUTHALAM
        </div>

        <p>
          Sea and island exploration experiences
          around Ramanathapuram and Rameswaram.
        </p>

        <div className="footer-copy">
          © 2026 Vanga Suthalam. All rights reserved.
        </div>

      </footer>

    </div>
  );
}

export default IslandApproval;
