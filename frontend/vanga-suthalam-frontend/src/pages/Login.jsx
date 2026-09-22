import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "./Login.css";

function Login() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    email: localStorage.getItem("vangaRememberEmail") || "",
    password: "",
  });

  const [showPassword, setShowPassword] = useState(false);

  const [rememberMe, setRememberMe] = useState(
    !!localStorage.getItem("vangaRememberEmail")
  );

  const [loading, setLoading] = useState(false);

  const [errors, setErrors] = useState({});

  const [loginMessage, setLoginMessage] = useState("");

  const [loginSuccess, setLoginSuccess] = useState(false);

  // =========================
  // HANDLE INPUT CHANGE
  // =========================

  const handleChange = (e) => {
    const { name, value } = e.target;

    setFormData((previous) => ({
      ...previous,
      [name]: value,
    }));

    setErrors((previous) => ({
      ...previous,
      [name]: "",
    }));

    setLoginMessage("");
    setLoginSuccess(false);
  };

  // =========================
  // VALIDATE FORM
  // =========================

  const validateForm = () => {
    const newErrors = {};

    const emailRegex =
      /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!formData.email.trim()) {
      newErrors.email =
        "Email address is required";
    } else if (!emailRegex.test(formData.email.trim())) {
      newErrors.email =
        "Enter a valid email address";
    }

    if (!formData.password) {
      newErrors.password =
        "Password is required";
    } else if (formData.password.length < 6) {
      newErrors.password =
        "Password must contain at least 6 characters";
    }

    setErrors(newErrors);

    return Object.keys(newErrors).length === 0;
  };

  // =========================
  // LOGIN API
  // =========================

  const handleSubmit = async (e) => {
    e.preventDefault();

    setLoginMessage("");
    setLoginSuccess(false);

    if (!validateForm()) {
      return;
    }

    setLoading(true);

    try {
      const response = await fetch(
        "https://vanga-suthalam.onrender.com/api/customer/login",
        {
          method: "POST",

          headers: {
            "Content-Type":
              "application/x-www-form-urlencoded",
          },

          body: new URLSearchParams({
            email: formData.email.trim(),
            password: formData.password,
          }),
        }
      );

      const data = await response.json();

      console.log(
        "Customer Login API Response:",
        data
      );

      // =========================
      // SUCCESS
      // =========================

      if (response.ok && data.success) {
        setLoginSuccess(true);

        setLoginMessage(
          "Login successful! Welcome to Vanga Suthalam."
        );

        // Save customer ID
        localStorage.setItem(
          "customerId",
          data.customerId
        );

        // Save customer email
        localStorage.setItem(
          "customerEmail",
          formData.email.trim()
        );

        // Remember email
        if (rememberMe) {
          localStorage.setItem(
            "vangaRememberEmail",
            formData.email.trim()
          );
        } else {
          localStorage.removeItem(
            "vangaRememberEmail"
          );
        }

        // Go to Home
        setTimeout(() => {
          navigate("/");
        }, 1000);
      }

      // =========================
      // INVALID LOGIN
      // =========================

      else {
        setLoginSuccess(false);

        setLoginMessage(
          data.message ||
            "Invalid email or password"
        );
      }
    }

    // =========================
    // CONNECTION ERROR
    // =========================

    catch (error) {
      console.error(
        "Customer Login API Error:",
        error
      );

      setLoginSuccess(false);

      setLoginMessage(
        "Unable to connect to server. Please make sure Tomcat is running."
      );
    }

    finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-page">

      {/* =========================
          BACKGROUND
      ========================== */}

      <div className="login-background"></div>

      <div className="login-overlay"></div>

      {/* =========================
          NAVBAR
      ========================== */}

      <nav className="login-navbar">

        <Link
          to="/"
          className="login-logo"
        >
          <span className="logo-icon">
            ⚓
          </span>

          <span>
            <strong>VANGA</strong>
            <small>SUTHALAM</small>
          </span>
        </Link>

        <div className="login-nav-links">

          <Link to="/">
            Home
          </Link>

          <Link to="/destinations">
            Destinations
          </Link>

          <Link to="/packages">
            Packages
          </Link>

          <Link to="/register">
            Register
          </Link>

        </div>

        <Link
          to="/register"
          className="nav-register"
        >
          Create Account
        </Link>

      </nav>

      {/* =========================
          MAIN
      ========================== */}

      <main className="login-main">

        {/* =========================
            LEFT HERO
        ========================== */}

        <section className="login-hero">

          <div className="hero-badge">
            🌊 EXPLORE • DISCOVER • ENJOY
          </div>

          <h1>
            WELCOME BACK TO

            <span>
              VANGA SUTHALAM
            </span>
          </h1>

          <p className="hero-description">
            Your next unforgettable sea and
            island adventure is waiting for you.
            Login to continue your journey
            across the beautiful waters of
            Ramanathapuram and Rameswaram.
          </p>

          <div className="login-features">

            {/* Feature 1 */}

            <div className="login-feature">

              <div className="feature-icon">
                🚤
              </div>

              <div>
                <h3>
                  Premium Boat Trips
                </h3>

                <p>
                  Comfortable & enjoyable journeys
                </p>
              </div>

            </div>

            {/* Feature 2 */}

            <div className="login-feature">

              <div className="feature-icon">
                🏝️
              </div>

              <div>
                <h3>
                  Island Exploration
                </h3>

                <p>
                  Discover beautiful marine destinations
                </p>
              </div>

            </div>

            {/* Feature 3 */}

            <div className="login-feature">

              <div className="feature-icon">
                🛡️
              </div>

              <div>
                <h3>
                  Safety First
                </h3>

                <p>
                  Secure trips with verified services
                </p>
              </div>

            </div>

          </div>

          <div className="adventure-text">

            <span>
              YOUR NEXT
            </span>

            <strong>
              ADVENTURE
            </strong>

            <span>
              BEGINS HERE...
            </span>

          </div>

        </section>

        {/* =========================
            LOGIN CARD
        ========================== */}

        <section className="login-card">

          <div className="login-card-header">

            <div className="login-user-icon">
              👤
            </div>

            <h2>
              Welcome Back!
            </h2>

            <p>
              Login to continue your adventure
            </p>

          </div>

          {/* =========================
              LOGIN MESSAGE
          ========================== */}

          {loginMessage && (
            <div
              className={
                loginSuccess
                  ? "login-success"
                  : "login-error"
              }
            >
              {loginSuccess
                ? "✓ "
                : "⚠️ "}

              {loginMessage}
            </div>
          )}

          {/* =========================
              FORM
          ========================== */}

          <form
            onSubmit={handleSubmit}
            noValidate
          >

            {/* EMAIL */}

            <div className="form-group">

              <label htmlFor="email">
                Email Address
              </label>

              <div
                className={`input-wrapper ${
                  errors.email
                    ? "input-error"
                    : ""
                }`}
              >

                <span className="input-icon">
                  ✉️
                </span>

                <input
                  type="email"
                  id="email"
                  name="email"
                  placeholder="Enter your email address"
                  value={formData.email}
                  onChange={handleChange}
                  autoComplete="email"
                  disabled={loading}
                />

              </div>

              {errors.email && (
                <span className="error-message">
                  {errors.email}
                </span>
              )}

            </div>

            {/* PASSWORD */}

            <div className="form-group">

              <label htmlFor="password">
                Password
              </label>

              <div
                className={`input-wrapper ${
                  errors.password
                    ? "input-error"
                    : ""
                }`}
              >

                <span className="input-icon">
                  🔒
                </span>

                <input
                  type={
                    showPassword
                      ? "text"
                      : "password"
                  }
                  id="password"
                  name="password"
                  placeholder="Enter your password"
                  value={formData.password}
                  onChange={handleChange}
                  autoComplete="current-password"
                  disabled={loading}
                />

                <button
                  type="button"
                  className="password-toggle"
                  onClick={() =>
                    setShowPassword(
                      !showPassword
                    )
                  }
                  aria-label={
                    showPassword
                      ? "Hide password"
                      : "Show password"
                  }
                  disabled={loading}
                >
                  {showPassword
                    ? "🙈"
                    : "👁️"}
                </button>

              </div>

              {errors.password && (
                <span className="error-message">
                  {errors.password}
                </span>
              )}

            </div>

            {/* =========================
                REMEMBER + FORGOT
            ========================== */}

            <div className="login-options">

              <label className="remember-me">

                <input
                  type="checkbox"
                  checked={rememberMe}
                  onChange={(e) =>
                    setRememberMe(
                      e.target.checked
                    )
                  }
                  disabled={loading}
                />

                <span>
                  Remember me
                </span>

              </label>

              <button
                type="button"
                className="forgot-password"
                onClick={() =>
                  alert(
                    "Password recovery will be connected to the backend soon."
                  )
                }
                disabled={loading}
              >
                Forgot Password?
              </button>

            </div>

            {/* =========================
                LOGIN BUTTON
            ========================== */}

            <button
              type="submit"
              className="login-button"
              disabled={loading}
            >

              {loading ? (
                <>
                  <span className="button-spinner"></span>

                  Signing In...
                </>
              ) : (
                <>
                  🔐 Sign In

                  <span>
                    →
                  </span>
                </>
              )}

            </button>

          </form>

          {/* =========================
              DIVIDER
          ========================== */}

          <div className="login-divider">
            <span>
              OR
            </span>
          </div>

          {/* =========================
              REGISTER
          ========================== */}

          <div className="register-prompt">

            <p>
              Don't have an account?
            </p>

            <Link to="/register">
              Create Your Account →
            </Link>

          </div>

          {/* =========================
              SECURITY
          ========================== */}

          <div className="secure-login">
            🔒 Your information is protected
            and secure
          </div>

        </section>

      </main>

      {/* =========================
          FOOTER
      ========================== */}

      <footer className="login-footer">

        <p>
          © 2026{" "}
          <strong>
            Vanga Suthalam
          </strong>
          . Sea & Island Exploration 🌊
        </p>

      </footer>

    </div>
  );
}

export default Login;
