import { BrowserRouter, Routes, Route } from "react-router-dom";

import Home from "./pages/Home";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Destinations from "./pages/Destinations";
import Packages from "./pages/Packages";
import Booking from "./pages/Booking";
import Payment from "./pages/Payment";
import Feedback from "./pages/Feedback";
import PaymentSuccess from "./pages/PaymentSuccess";
import IslandApproval from "./pages/IslandApproval";
import CaptainAssignment from "./pages/CaptainAssignment";
import SafetyVerification from "./pages/SafetyVerification";
import TripManagement from "./pages/TripManagement";
import CaptainDashboard from "./pages/CaptainDashboard";
import TripStatus from "./pages/TripStatus";
import MyBookings from "./pages/MyBookings";
import About from "./pages/About";
import Contact from "./pages/Contact";


function App() {
  return (
    <BrowserRouter>
      <Routes>

        {/* Home */}
        <Route
          path="/"
          element={<Home />}
        />

        {/* Customer Authentication */}
        <Route
          path="/login"
          element={<Login />}
        />

        <Route
          path="/register"
          element={<Register />}
        />

        {/* Destinations */}
        <Route
          path="/destinations"
          element={<Destinations />}
        />

        {/* Packages */}
        <Route
          path="/packages"
          element={<Packages />}
        />

        {/* Booking */}
        <Route
          path="/booking"
          element={<Booking />}
        />

        {/* Payment */}
        <Route
          path="/payment"
          element={<Payment />}
        />

        {/* Payment Success */}
        <Route
          path="/payment-success"
          element={<PaymentSuccess />}
        />

        {/* Island Approval */}
        <Route
          path="/island-approval"
          element={<IslandApproval />}
        />

        {/* Feedback */}
        <Route
          path="/feedback"
          element={<Feedback />}
        />

        {/* Captain & Boat Assignment */}
        <Route
          path="/captain-assignment"
          element={<CaptainAssignment />}
        />


<Route
  path="/safety-verification"
  element={<SafetyVerification />}
/>


<Route
  path="/trip-management"
  element={<TripManagement />}
/>


<Route
  path="/captain-dashboard"
  element={<CaptainDashboard />}
/>

<Route
  path="/trip-status"
  element={<TripStatus />}
/>

<Route path="/my-bookings" element={<MyBookings />} />

<Route path="/about" element={<About />} />
<Route path="/contact" element={<Contact />} />


        

      </Routes>
    </BrowserRouter>
  );
}

export default App;