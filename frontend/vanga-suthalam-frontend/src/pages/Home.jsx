import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import "./Home.css";

function Home() {



  // =====================================================
  // HD BACKGROUND IMAGES
  // Changes automatically every 10 seconds
  // =====================================================

  const backgroundImages = [
    {
      url: "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=2400&q=90",
      title: "Sea Adventure"
    },

    {
      url: "https://images.unsplash.com/photo-1544551763-46a013bb70d5?auto=format&fit=crop&w=2400&q=90",
      title: "Boat Exploration"
    },

    {
      // Tropical island / palm trees / ocean
      url: "https://images.unsplash.com/photo-1548013146-72479768bada?auto=format&fit=crop&w=2400&q=90",
      title: "Mulli Theevu"
    },

    {
      url: "https://images.unsplash.com/photo-1469474968028-56623f02e42e?auto=format&fit=crop&w=2400&q=90",
      title: "Coastal Nature"
    }
  ];


  // =====================================================
  // CURRENT IMAGE
  // =====================================================

  const [currentImage, setCurrentImage] = useState(0);


  // =====================================================
  // AUTOMATIC IMAGE CHANGE
  // 10 SECONDS
  // =====================================================

  useEffect(() => {

    const timer = setInterval(() => {

      setCurrentImage((previous) => {

        return (
          (previous + 1) %
          backgroundImages.length
        );

      });

    }, 10000);

    return () => clearInterval(timer);

  }, [backgroundImages.length]);


  // =====================================================
  // NEXT IMAGE
  // =====================================================

  const nextImage = () => {

    setCurrentImage(
      (currentImage + 1) %
      backgroundImages.length
    );

  };


  // =====================================================
  // PREVIOUS IMAGE
  // =====================================================

  const previousImage = () => {

    setCurrentImage(
      (currentImage - 1 + backgroundImages.length) %
      backgroundImages.length
    );

  };


  return (

    <div className="home-page">


      {/* =================================================
          NAVBAR
      ================================================= */}

      <nav className="navbar">


        {/* LOGO */}

        <div className="logo-area">

          <div className="logo-circle">
            ⚓
          </div>

          <div className="logo-text">

            <h2>
              VANGA SUTHALAM
            </h2>

            <span>
              Sea & Island Exploration
            </span>

          </div>

        </div>


        {/* NAVIGATION */}

        <div className="nav-links">


          <Link
            to="/"
            className="active"
          >
            Home
          </Link>


          <Link to="/destinations">
            Destinations
          </Link>


          <Link to="/packages">
            Packages
          </Link>


          <Link to="/my-bookings" className="my-bookings-link">
  📋 My Bookings
</Link>


          


          <Link to="/about">
            About
          </Link>


          <Link to="/contact">
            Contact
          </Link>


          <span className="search-icon">
            🔍
          </span>


          <Link
            to="/login"
            className="login-btn"
          >
            👤 Login
          </Link>


          <Link
            to="/register"
            className="register-btn"
          >
            👥 Register
          </Link>


        </div>

      </nav>


      {/* =================================================
          HERO SECTION
      ================================================= */}

      <section
        className="hero-section"
        style={{
          backgroundImage:
            `url("${backgroundImages[currentImage].url}")`
        }}
      >


        {/* DARK OVERLAY */}

        <div className="hero-overlay"></div>


        {/* HERO CONTENT */}

        <div className="hero-content">


          <p className="hero-small">
            EXPLORE
            &nbsp; • &nbsp;
            DISCOVER
            &nbsp; • &nbsp;
            EXPERIENCE
          </p>


          <h1>

            Discover the Beauty of

            <br />

            <span>
              Ramanathapuram & Rameswaram
            </span>

          </h1>


          <p className="hero-description">

            Experience unforgettable sea and island
            adventures with safe, comfortable and
            enjoyable boat trips.

            Explore beautiful coastal waters,
            approved marine routes and create
            memories for a lifetime.

          </p>


          {/* =================================================
              BUTTONS
          ================================================= */}

          <div className="hero-buttons">


            <Link
              to="/destinations"
              className="explore-button"
            >

              🧭

              <span>
                Explore Destinations
              </span>

              <strong>
                →
              </strong>

            </Link>


            <Link
              to="/packages"
              className="packages-button"
            >

              🎁

              <span>
                View Packages
              </span>

            </Link>


          </div>


        </div>


        {/* =================================================
            LEFT ARROW
        ================================================= */}

        <button
          className="slider-button slider-left"
          onClick={previousImage}
        >
          ‹
        </button>


        {/* =================================================
            RIGHT ARROW
        ================================================= */}

        <button
          className="slider-button slider-right"
          onClick={nextImage}
        >
          ›
        </button>


        {/* =================================================
            SLIDER DOTS
        ================================================= */}

        <div className="slider-dots">

          {backgroundImages.map((image, index) => (

            <button
              key={image.title}
              className={
                index === currentImage
                  ? "dot active"
                  : "dot"
              }
              onClick={() => setCurrentImage(index)}
              aria-label={`Show ${image.title}`}
            />

          ))}

        </div>


        {/* =================================================
            FEATURE BAR
        ================================================= */}

        <div className="feature-bar">


          {/* SAFETY */}

          <div className="feature">

            <div className="feature-icon">
              🛡️
            </div>

            <div>

              <h3>
                Safe & Secure
              </h3>

              <p>
                Certified Captains & Equipment
              </p>

            </div>

          </div>


          <div className="feature-divider"></div>


          {/* BOATS */}

          <div className="feature">

            <div className="feature-icon">
              🚤
            </div>

            <div>

              <h3>
                Comfortable Boats
              </h3>

              <p>
                Well Maintained & Spacious
              </p>

            </div>

          </div>


          <div className="feature-divider"></div>


          {/* ISLAND */}

          <div className="feature">

            <div className="feature-icon">
              🏝️
            </div>

            <div>

              <h3>
                Island Exploration
              </h3>

              <p>
                Unique & Beautiful Islands
              </p>

            </div>

          </div>


          <div className="feature-divider"></div>


          {/* SUPPORT */}

          <div className="feature">

            <div className="feature-icon">
              🎧
            </div>

            <div>

              <h3>
                24/7 Support
              </h3>

              <p>
                Always Here For You
              </p>

            </div>

          </div>


        </div>


      </section>


      {/* =================================================
          DESTINATIONS SECTION
      ================================================= */}

      <section className="destinations-section">


        <div className="destination-header">


          <div>

            <p className="section-label">
              POPULAR DESTINATIONS
            </p>


            <h2>
              Explore Our Beautiful Destinations
            </h2>


            <p className="section-description">

              Discover stunning coastal destinations,
              crystal clear waters and unforgettable
              experiences around Ramanathapuram
              and Rameswaram.

            </p>

          </div>


          <Link
            to="/destinations"
            className="view-all-button"
          >
            View All Destinations →
          </Link>


        </div>


        {/* DESTINATION CARDS */}

        <div className="destination-cards">


          {/* RAMESWARAM */}

          <div className="destination-card">

            <div
              className="destination-photo"
              style={{
                backgroundImage:
                  `url("${backgroundImages[0].url}")`
              }}
            >

              <span>
                📍 Rameswaram
              </span>

            </div>


            <div className="destination-info">

              <h3>
                Rameswaram
              </h3>

              <p>
                Beautiful coastal exploration
                and unforgettable sea experiences.
              </p>

            </div>

          </div>


          {/* DHANUSHKODI */}

          <div className="destination-card">

            <div
              className="destination-photo"
              style={{
                backgroundImage:
                  `url("${backgroundImages[1].url}")`
              }}
            >

              <span>
                📍 Dhanushkodi
              </span>

            </div>


            <div className="destination-info">

              <h3>
                Dhanushkodi
              </h3>

              <p>
                Explore the unique beauty
                of the coastal region.
              </p>

            </div>

          </div>


          {/* MULLI THEEVU */}

          <div className="destination-card">

            <div
              className="destination-photo"
              style={{
                backgroundImage:
                  `url("${backgroundImages[2].url}")`
              }}
            >

              <span>
                📍 Mulli Theevu
              </span>

            </div>


            <div className="destination-info">

              <h3>
                Mulli Theevu
              </h3>

              <p>
                Approved island exploration
                experiences.
              </p>

            </div>

          </div>


          {/* APPA THEEVU */}

          <div className="destination-card">

            <div
              className="destination-photo"
              style={{
                backgroundImage:
                  `url("${backgroundImages[3].url}")`
              }}
            >

              <span>
                📍 Appa Theevu
              </span>

            </div>


            <div className="destination-info">

              <h3>
                Appa Theevu
              </h3>

              <p>
                Marine exploration experiences
                subject to approval.
              </p>

            </div>

          </div>


        </div>

      </section>


      {/* =================================================
          CTA
      ================================================= */}

      <section className="cta-section">


        <div>

          <p>
            READY FOR YOUR ADVENTURE?
          </p>


          <h2>
            Start Your Sea Adventure Today
          </h2>


          <span>
            Choose your destination, select your
            package and begin your Vanga Suthalam journey.
          </span>

        </div>


        <Link
          to="/register"
          className="cta-button"
        >
          Start Your Journey →
        </Link>


      </section>


      {/* =================================================
          FOOTER
      ================================================= */}

      <footer className="footer">


        <div className="footer-logo">


          <div className="logo-circle">
            ⚓
          </div>


          <div>

            <h2>
              VANGA SUTHALAM
            </h2>

            <p>
              Sea & Island Exploration
            </p>

          </div>


        </div>


        <div className="footer-right">

          <p>
            📍 Ramanathapuram & Rameswaram
          </p>

          <p>
            © 2026 Vanga Suthalam
          </p>

        </div>


      </footer>


    </div>

  );
}

export default Home;