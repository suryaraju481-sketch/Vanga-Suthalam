import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "./Register.css";

function Register() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    name: "",
    mobile: "",
    email: "",
    password: "",
    confirmPassword: "",
    address: "",
  });

  const [agreeTerms, setAgreeTerms] = useState(false);
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  // =========================================================
  // HANDLE INPUT
  // =========================================================

  const handleChange = (e) => {
    const { name, value } = e.target;

    setFormData((previous) => ({
      ...previous,
      [name]: value,
    }));

    setError("");
    setSuccess("");
  };

  // =========================================================
  // PASSWORD STRENGTH
  // =========================================================

  const getPasswordStrength = () => {
    let score = 0;

    if (formData.password.length >= 8) score++;
    if (/[A-Z]/.test(formData.password)) score++;
    if (/[a-z]/.test(formData.password)) score++;
    if (/[0-9]/.test(formData.password)) score++;
    if (/[^A-Za-z0-9]/.test(formData.password)) score++;

    return score;
  };

  const passwordStrength = getPasswordStrength();

  const getStrengthText = () => {
    if (!formData.password) return "";

    if (passwordStrength <= 2) return "Weak";
    if (passwordStrength === 3) return "Medium";
    if (passwordStrength === 4) return "Strong";

    return "Very Strong";
  };

  // =========================================================
  // REGISTER API
  // =========================================================

  const handleSubmit = async (e) => {
    e.preventDefault();

    setError("");
    setSuccess("");

    // Name
    if (!formData.name.trim()) {
      setError("Please enter your full name.");
      return;
    }

    // Mobile
    if (!/^[6-9][0-9]{9}$/.test(formData.mobile)) {
      setError(
        "Please enter a valid 10-digit mobile number."
      );
      return;
    }

    // Email
    if (
      !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(
        formData.email.trim()
      )
    ) {
      setError("Please enter a valid email address.");
      return;
    }

    // Password
    if (formData.password.length < 8) {
      setError(
        "Password must contain at least 8 characters."
      );
      return;
    }

    // Password strength
    if (passwordStrength < 3) {
      setError(
        "Please create a stronger password using uppercase, lowercase and numbers."
      );
      return;
    }

    // Confirm password
    if (
      formData.password !==
      formData.confirmPassword
    ) {
      setError("Passwords do not match.");
      return;
    }

    // Address
    if (!formData.address.trim()) {
      setError("Please enter your address.");
      return;
    }

    // Terms
    if (!agreeTerms) {
      setError(
        "Please accept the Terms & Conditions."
      );
      return;
    }

    setLoading(true);

    try {
      // =====================================================
      // CALL JAVA BACKEND
      // =====================================================

      const response = await fetch(
        "https://vanga-suthalam.onrender.com/api/customer/register",
        {
          method: "POST",

          headers: {
            "Content-Type":
              "application/x-www-form-urlencoded",
          },

          body: new URLSearchParams({
            name: formData.name.trim(),
            mobile: formData.mobile.trim(),
            email: formData.email.trim(),
            password: formData.password,
            address: formData.address.trim(),
          }),
        }
      );

      const data = await response.json();

      console.log(
        "Customer Register API Response:",
        data
      );

      // =====================================================
      // SUCCESS
      // =====================================================

      if (
        response.ok &&
        data.success
      ) {
        setSuccess(
          "Registration successful! Redirecting to login..."
        );

        // Clear form
        setFormData({
          name: "",
          mobile: "",
          email: "",
          password: "",
          confirmPassword: "",
          address: "",
        });

        setAgreeTerms(false);

        // Go to login
        setTimeout(() => {
          navigate("/login");
        }, 1500);
      }

      // =====================================================
      // BACKEND ERROR
      // =====================================================

      else {
        setError(
          data.message ||
            "Customer registration failed."
        );
      }
    }

    // =======================================================
    // CONNECTION ERROR
    // =======================================================

    catch (error) {
      console.error(
        "Customer Register API Error:",
        error
      );

      setError(
        "Unable to connect to server. Please make sure Tomcat is running."
      );
    }

    finally {
      setLoading(false);
    }
  };

  return (
    <div className="register-page">

      {/* =====================================================
          BACKGROUND
          ===================================================== */}

      <div className="register-background"></div>

      <div className="register-overlay"></div>

      {/* =====================================================
          NAVBAR
          ===================================================== */}

      <header className="register-navbar">

        {/* LOGO */}

        <Link
          to="/"
          className="register-brand"
        >

          <div className="brand-anchor">
            ⚓
          </div>

          <div className="brand-details">

            <h2>VANGA</h2>
            <h2>SUTHALAM</h2>

            <span>
              Explore • Discover • Experience
            </span>

          </div>

        </Link>

        {/* NAVIGATION */}

        <nav className="register-navigation">

          <Link to="/">
            Home
          </Link>

          <Link to="/destinations">
            Destinations
          </Link>

          <Link to="/packages">
            Packages
          </Link>

          <a href="#about">
            About
          </a>

        </nav>

        {/* LOGIN */}

        <Link
          to="/login"
          className="navbar-login"
        >

          <span className="login-person">
            ♙
          </span>

          Login

        </Link>

      </header>

      {/* =====================================================
          MAIN
          ===================================================== */}

      <main className="register-main">

        {/* ===================================================
            LEFT SIDE
            =================================================== */}

        <section className="register-left">

          <div className="welcome-label">
            WELCOME TO VANGA SUTHALAM
          </div>

          <h1>
            Create Your
            <br />
            <span>Account</span>
          </h1>

          <p className="register-intro">
            Join us and discover unforgettable sea
            adventures, island exploration, fishing
            experiences and beautiful coastal journeys
            around Ramanathapuram & Rameswaram.
          </p>

          {/* FEATURE 1 */}

          <div className="feature">

            <div className="feature-icon feature-blue">
              🚤
            </div>

            <div className="feature-content">

              <h3>
                Premium Boat Trips
              </h3>

              <p>
                Comfortable & memorable journeys
              </p>

            </div>

          </div>

          {/* FEATURE 2 */}

          <div className="feature">

            <div className="feature-icon feature-green">
              🌴
            </div>

            <div className="feature-content">

              <h3>
                Island Exploration
              </h3>

              <p>
                Discover beautiful marine destinations
              </p>

            </div>

          </div>

          {/* FEATURE 3 */}

          <div className="feature">

            <div className="feature-icon feature-purple">
              🛡️
            </div>

            <div className="feature-content">

              <h3>
                Safety First
              </h3>

              <p>
                Designed around a safety-focused journey
              </p>

            </div>

          </div>

          {/* ADVENTURE */}

          <div className="adventure">

            <div className="adventure-line"></div>

            <div>

              <span>
                Your Next Adventure
              </span>

              <strong>
                Begins Here...
              </strong>

            </div>

          </div>

        </section>

        {/* ===================================================
            RIGHT REGISTER CARD
            =================================================== */}

        <section className="register-right">

          <div className="register-card">

            {/* CARD HEADER */}

            <div className="card-heading">

              <div className="card-user-icon">
                👤
              </div>

              <div>

                <h2>
                  Register
                </h2>

                <p>
                  Create your account to get started
                </p>

              </div>

            </div>

            {/* SUCCESS */}

            {success && (
              <div className="success-box">
                ✓ {success}
              </div>
            )}

            {/* ERROR */}

            {error && (
              <div className="error-box">
                ⚠ {error}
              </div>
            )}

            {/* FORM */}

            <form onSubmit={handleSubmit}>

              {/* FULL NAME */}

              <div className="form-group">

                <label>
                  Full Name *
                </label>

                <div className="input-container">

                  <span className="input-symbol">
                    ♙
                  </span>

                  <input
                    type="text"
                    name="name"
                    placeholder="Enter your full name"
                    value={formData.name}
                    onChange={handleChange}
                    autoComplete="name"
                    disabled={loading}
                  />

                </div>

              </div>

              {/* MOBILE + EMAIL */}

              <div className="form-row">

                <div className="form-group">

                  <label>
                    Mobile Number *
                  </label>

                  <div className="input-container">

                    <span className="input-symbol">
                      ☎
                    </span>

                    <input
                      type="tel"
                      name="mobile"
                      placeholder="10-digit mobile number"
                      value={formData.mobile}
                      onChange={handleChange}
                      maxLength="10"
                      autoComplete="tel"
                      disabled={loading}
                    />

                  </div>

                </div>

                <div className="form-group">

                  <label>
                    Email Address *
                  </label>

                  <div className="input-container">

                    <span className="input-symbol">
                      ✉
                    </span>

                    <input
                      type="email"
                      name="email"
                      placeholder="you@example.com"
                      value={formData.email}
                      onChange={handleChange}
                      autoComplete="email"
                      disabled={loading}
                    />

                  </div>

                </div>

              </div>

              {/* PASSWORD */}

              <div className="form-group">

                <label>
                  Password *
                </label>

                <div className="input-container">

                  <span className="input-symbol">
                    🔒
                  </span>

                  <input
                    type={
                      showPassword
                        ? "text"
                        : "password"
                    }
                    name="password"
                    placeholder="Create a strong password"
                    value={formData.password}
                    onChange={handleChange}
                    autoComplete="new-password"
                    disabled={loading}
                  />

                  <button
                    type="button"
                    className="eye-button"
                    onClick={() =>
                      setShowPassword(
                        !showPassword
                      )
                    }
                    disabled={loading}
                  >
                    {showPassword
                      ? "🙈"
                      : "👁"}
                  </button>

                </div>

                {/* PASSWORD STRENGTH */}

                {formData.password && (

                  <div className="password-strength">

                    <div className="strength-bars">

                      {[1, 2, 3, 4, 5].map(
                        (number) => (
                          <span
                            key={number}
                            className={
                              number <=
                              passwordStrength
                                ? `filled strength-${passwordStrength}`
                                : ""
                            }
                          ></span>
                        )
                      )}

                    </div>

                    <div className="strength-result">

                      <span>
                        Password strength
                      </span>

                      <strong>
                        {getStrengthText()}
                      </strong>

                    </div>

                  </div>

                )}

              </div>

              {/* CONFIRM PASSWORD */}

              <div className="form-group">

                <label>
                  Confirm Password *
                </label>

                <div className="input-container">

                  <span className="input-symbol">
                    🔒
                  </span>

                  <input
                    type={
                      showConfirmPassword
                        ? "text"
                        : "password"
                    }
                    name="confirmPassword"
                    placeholder="Confirm your password"
                    value={formData.confirmPassword}
                    onChange={handleChange}
                    autoComplete="new-password"
                    disabled={loading}
                  />

                  <button
                    type="button"
                    className="eye-button"
                    onClick={() =>
                      setShowConfirmPassword(
                        !showConfirmPassword
                      )
                    }
                    disabled={loading}
                  >
                    {showConfirmPassword
                      ? "🙈"
                      : "👁"}
                  </button>

                </div>

              </div>

              {/* ADDRESS */}

              <div className="form-group">

                <label>
                  Address *
                </label>

                <div className="textarea-container">

                  <span className="textarea-symbol">
                    📍
                  </span>

                  <textarea
                    name="address"
                    placeholder="Enter your address"
                    value={formData.address}
                    onChange={handleChange}
                    rows="3"
                    disabled={loading}
                  ></textarea>

                </div>

              </div>

              {/* TERMS */}

              <div className="terms">

                <input
                  type="checkbox"
                  checked={agreeTerms}
                  onChange={(e) => {
                    setAgreeTerms(
                      e.target.checked
                    );

                    setError("");
                  }}
                  disabled={loading}
                />

                <p>
                  I agree to the{" "}
                  <a href="#terms">
                    Terms & Conditions
                  </a>{" "}
                  and{" "}
                  <a href="#privacy">
                    Privacy Policy
                  </a>
                </p>

              </div>

              {/* CREATE ACCOUNT */}

              <button
                type="submit"
                className="create-account"
                disabled={loading}
              >

                {loading ? (
                  <>
                    <span className="spinner"></span>
                    Creating Account...
                  </>
                ) : (
                  <>
                    <span>
                      ♙
                    </span>

                    Create My Account

                    <span>
                      →
                    </span>
                  </>
                )}

              </button>

            </form>

            {/* LOGIN */}

            <div className="already-account">

              Already have an account?

              <Link to="/login">
                Login here
              </Link>

            </div>

          </div>

        </section>

      </main>

      {/* =====================================================
          FOOTER
          ===================================================== */}

      <footer className="register-footer">

        <span>
          © 2026 Vanga Suthalam.
        </span>

        <span>
          Sea & Island Exploration
        </span>

        <span>
          🌊
        </span>

      </footer>

    </div>
  );
}

export default Register;
