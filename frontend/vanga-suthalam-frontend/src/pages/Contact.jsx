
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Contact.css";

function Contact() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    name: "",
    email: "",
    mobile: "",
    subject: "",
    message: "",
  });

  const [submitted, setSubmitted] = useState(false);

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    setSubmitted(true);

    setFormData({
      name: "",
      email: "",
      mobile: "",
      subject: "",
      message: "",
    });

    setTimeout(() => {
      setSubmitted(false);
    }, 4000);
  };

  return (
    <div className="contact-page">

      {/* Navbar */}
      <nav className="contact-navbar">

        <div
          className="contact-logo"
          onClick={() => navigate("/")}
        >
          🌊 VANGA SUTHALAM
        </div>

        <div className="contact-nav-links">
          <button onClick={() => navigate("/")}>
            Home
          </button>

          <button onClick={() => navigate("/destinations")}>
            Destinations
          </button>

          <button onClick={() => navigate("/packages")}>
            Packages
          </button>

          <button onClick={() => navigate("/about")}>
            About
          </button>

          <button onClick={() => navigate("/feedback")}>
            Feedback
          </button>

          <button
            className="contact-active-link"
            onClick={() => navigate("/contact")}
          >
            Contact
          </button>
        </div>

        <div className="contact-nav-actions">

          <button
            className="contact-login-btn"
            onClick={() => navigate("/login")}
          >
            Login
          </button>

          <button
            className="contact-register-btn"
            onClick={() => navigate("/register")}
          >
            Register
          </button>

        </div>
      </nav>


      {/* Hero */}
      <section className="contact-hero">

        <div className="contact-hero-overlay"></div>

        <div className="contact-hero-content">

          <span>🌊 GET IN TOUCH</span>

          <h1>
            Contact
            <strong> Vanga Suthalam</strong>
          </h1>

          <p>
            Have a question about destinations, packages,
            bookings or your sea adventure?
            We are here to help.
          </p>

        </div>

      </section>


      {/* Contact Information */}
      <section className="contact-info-section">

        <div className="contact-heading">

          <span>CONTACT US</span>

          <h2>
            Let's Start Your
            <strong> Sea Adventure</strong>
          </h2>

          <p>
            Reach out to us for information about
            destinations, boat trips, packages and bookings.
          </p>

        </div>


        <div className="contact-info-grid">

          <div className="contact-info-card">

            <div className="contact-icon">
              📍
            </div>

            <h3>Our Location</h3>

            <p>
              Ramanathapuram
            </p>

            <span>
              Tamil Nadu, India
            </span>

          </div>


          <div className="contact-info-card">

            <div className="contact-icon">
              📞
            </div>

            <h3>Phone</h3>

            <p>
              +91 98765 43210
            </p>

            <span>
              Available for customer support
            </span>

          </div>


          <div className="contact-info-card">

            <div className="contact-icon">
              ✉️
            </div>

            <h3>Email</h3>

            <p>
              support@vangasuthalam.com
            </p>

            <span>
              Send us your questions anytime
            </span>

          </div>


          <div className="contact-info-card">

            <div className="contact-icon">
              🕒
            </div>

            <h3>Support Hours</h3>

            <p>
              Monday - Saturday
            </p>

            <span>
              9:00 AM - 6:00 PM
            </span>

          </div>

        </div>

      </section>


      {/* Contact Form */}
      <section className="contact-form-section">

        <div className="contact-form-container">

          <div className="contact-form-intro">

            <span>
              SEND A MESSAGE
            </span>

            <h2>
              We Would Love To
              <strong> Hear From You</strong>
            </h2>

            <p>
              Whether you need help with a booking,
              want to know more about an island,
              or have a suggestion for Vanga Suthalam,
              send us a message.
            </p>

            <div className="contact-form-highlights">

              <div>
                <span>🏝️</span>
                <p>Island destinations</p>
              </div>

              <div>
                <span>🚤</span>
                <p>Boat experiences</p>
              </div>

              <div>
                <span>🎣</span>
                <p>Fishing packages</p>
              </div>

              <div>
                <span>🛡️</span>
                <p>Safety support</p>
              </div>

            </div>

          </div>


          <div className="contact-form-box">

            {submitted && (
              <div className="contact-success">
                ✅ Your message has been submitted successfully!
              </div>
            )}

            <form onSubmit={handleSubmit}>

              <div className="contact-form-row">

                <div className="contact-field">

                  <label>
                    Your Name
                  </label>

                  <input
                    type="text"
                    name="name"
                    placeholder="Enter your name"
                    value={formData.name}
                    onChange={handleChange}
                    required
                  />

                </div>


                <div className="contact-field">

                  <label>
                    Email Address
                  </label>

                  <input
                    type="email"
                    name="email"
                    placeholder="Enter your email"
                    value={formData.email}
                    onChange={handleChange}
                    required
                  />

                </div>

              </div>


              <div className="contact-form-row">

                <div className="contact-field">

                  <label>
                    Mobile Number
                  </label>

                  <input
                    type="tel"
                    name="mobile"
                    placeholder="Enter your mobile number"
                    value={formData.mobile}
                    onChange={handleChange}
                    required
                  />

                </div>


                <div className="contact-field">

                  <label>
                    Subject
                  </label>

                  <input
                    type="text"
                    name="subject"
                    placeholder="What is your question?"
                    value={formData.subject}
                    onChange={handleChange}
                    required
                  />

                </div>

              </div>


              <div className="contact-field">

                <label>
                  Your Message
                </label>

                <textarea
                  name="message"
                  rows="6"
                  placeholder="Write your message here..."
                  value={formData.message}
                  onChange={handleChange}
                  required
                ></textarea>

              </div>


              <button
                type="submit"
                className="contact-submit-btn"
              >
                Send Message →
              </button>

            </form>

          </div>

        </div>

      </section>


      {/* Island Section */}
      <section className="contact-island-section">

        <div className="contact-island-overlay"></div>

        <div className="contact-island-content">

          <span>
            🏝️ ISLAND EXPLORER
          </span>

          <h2>
            Ready to Explore?
          </h2>

          <p>
            Discover Mulli Theevu, Desert Island,
            Appa Theevu, Valai Theevu and Muyal Theevu
            with Vanga Suthalam.
          </p>

          <button
            onClick={() => navigate("/destinations")}
          >
            Explore Destinations →
          </button>

        </div>

      </section>


      {/* Footer */}
      <footer className="contact-footer">

        <div className="contact-footer-brand">
          🌊 VANGA SUTHALAM
        </div>

        <p>
          Sea and island exploration experiences
          around Ramanathapuram and Rameswaram.
        </p>

        <div className="contact-footer-links">

          <button onClick={() => navigate("/")}>
            Home
          </button>

          <button onClick={() => navigate("/destinations")}>
            Destinations
          </button>

          <button onClick={() => navigate("/packages")}>
            Packages
          </button>

          <button onClick={() => navigate("/about")}>
            About
          </button>

          <button onClick={() => navigate("/contact")}>
            Contact
          </button>

        </div>

        <span>
          © 2026 Vanga Suthalam. All rights reserved.
        </span>

      </footer>

    </div>
  );
}

export default Contact;

