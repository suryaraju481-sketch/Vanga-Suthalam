package com.vangasuthalam.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import com.vangasuthalam.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/api/trips")
public class TripServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // =========================
    // GET - API Test
    // =========================
    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        out.print("""
            {
                "success": true,
                "message": "Trip API is working successfully."
            }
            """);

        out.flush();
    }

    // =========================
    // POST - Start Trip
    // =========================
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        String bookingIdParam =
                request.getParameter("bookingId");

        String action =
                request.getParameter("action");

        if (bookingIdParam == null ||
            bookingIdParam.trim().isEmpty()) {

            out.print("""
                {
                    "success": false,
                    "message": "Booking ID is required."
                }
                """);

            return;
        }

        int bookingId;

        try {
            bookingId = Integer.parseInt(bookingIdParam);
        } catch (NumberFormatException e) {

            out.print("""
                {
                    "success": false,
                    "message": "Invalid Booking ID."
                }
                """);

            return;
        }

        if (action == null || action.trim().isEmpty()) {
            action = "START";
        }

        action = action.toUpperCase();

        try (Connection con =
                     DBConnection.getConnection()) {

            // =========================
            // Find Booking + Trip
            // =========================

            String findSql =
                    "SELECT t.trip_id, t.trip_status, "
                  + "t.trip_start_datetime, "
                  + "t.trip_end_datetime, "
                  + "b.booking_status "
                  + "FROM trips t "
                  + "JOIN bookings b "
                  + "ON t.booking_id = b.booking_id "
                  + "WHERE t.booking_id = ? "
                  + "ORDER BY t.trip_id DESC "
                  + "LIMIT 1";

            int tripId = 0;
            String tripStatus = null;
            String bookingStatus = null;

            try (PreparedStatement ps =
                         con.prepareStatement(findSql)) {

                ps.setInt(1, bookingId);

                try (ResultSet rs =
                             ps.executeQuery()) {

                    if (!rs.next()) {

                        out.print("""
                            {
                                "success": false,
                                "message": "Trip not found. Please assign a captain and boat first."
                            }
                            """);

                        return;
                    }

                    tripId =
                            rs.getInt("trip_id");

                    tripStatus =
                            rs.getString("trip_status");

                    bookingStatus =
                            rs.getString("booking_status");
                }
            }

            // =========================
            // START TRIP
            // =========================

            if ("START".equals(action)) {

                if ("STARTED".equalsIgnoreCase(tripStatus)) {

                    out.print("""
                        {
                            "success": false,
                            "message": "Trip has already been started."
                        }
                        """);

                    return;
                }

                if ("COMPLETED".equalsIgnoreCase(tripStatus)) {

                    out.print("""
                        {
                            "success": false,
                            "message": "Trip is already completed."
                        }
                        """);

                    return;
                }

                // -------------------------
                // Check Payment
                // -------------------------

                String paymentSql =
                        "SELECT payment_status "
                      + "FROM payments "
                      + "WHERE booking_id = ? "
                      + "ORDER BY payment_id DESC "
                      + "LIMIT 1";

                boolean paymentConfirmed = false;

                try (PreparedStatement ps =
                             con.prepareStatement(paymentSql)) {

                    ps.setInt(1, bookingId);

                    try (ResultSet rs =
                                 ps.executeQuery()) {

                        if (rs.next()) {

                            String paymentStatus =
                                    rs.getString("payment_status");

                            paymentConfirmed =
                                    "CONFIRMED"
                                    .equalsIgnoreCase(
                                            paymentStatus);
                        }
                    }
                }

                if (!paymentConfirmed) {

                    out.print("""
                        {
                            "success": false,
                            "message": "Payment must be confirmed before starting the trip."
                        }
                        """);

                    return;
                }

                // -------------------------
                // Check Safety
                // -------------------------

                String safetySql =
                        "SELECT safety_status "
                      + "FROM safety_checks "
                      + "WHERE booking_id = ? "
                      + "ORDER BY safety_check_id DESC "
                      + "LIMIT 1";

                boolean safetyApproved = false;

                try (PreparedStatement ps =
                             con.prepareStatement(safetySql)) {

                    ps.setInt(1, bookingId);

                    try (ResultSet rs =
                                 ps.executeQuery()) {

                        if (rs.next()) {

                            String safetyStatus =
                                    rs.getString("safety_status");

                            safetyApproved =
                                    "APPROVED"
                                    .equalsIgnoreCase(
                                            safetyStatus);
                        }
                    }
                }

                if (!safetyApproved) {

                    out.print("""
                        {
                            "success": false,
                            "message": "Safety verification must be approved before starting the trip."
                        }
                        """);

                    return;
                }

                // -------------------------
                // Start Trip
                // -------------------------

                String updateSql =
                        "UPDATE trips "
                      + "SET trip_start_datetime = NOW(), "
                      + "trip_status = 'STARTED' "
                      + "WHERE trip_id = ?";

                try (PreparedStatement ps =
                             con.prepareStatement(updateSql)) {

                    ps.setInt(1, tripId);

                    ps.executeUpdate();
                }

                // Update booking status

                String bookingSql =
                        "UPDATE bookings "
                      + "SET booking_status = 'IN_PROGRESS' "
                      + "WHERE booking_id = ?";

                try (PreparedStatement ps =
                             con.prepareStatement(bookingSql)) {

                    ps.setInt(1, bookingId);

                    ps.executeUpdate();
                }

                out.print("{");
                out.print("\"success\":true,");
                out.print("\"message\":\"Trip started successfully.\",");
                out.print("\"tripId\":" + tripId + ",");
                out.print("\"bookingId\":" + bookingId + ",");
                out.print("\"tripStatus\":\"STARTED\"");
                out.print("}");

                return;
            }

            // =========================
            // COMPLETE TRIP
            // =========================

            if ("COMPLETE".equals(action)) {

                if ("COMPLETED".equalsIgnoreCase(tripStatus)) {

                    out.print("""
                        {
                            "success": false,
                            "message": "Trip is already completed."
                        }
                        """);

                    return;
                }

                if (!"STARTED".equalsIgnoreCase(tripStatus)) {

                    out.print("""
                        {
                            "success": false,
                            "message": "Trip must be started before it can be completed."
                        }
                        """);

                    return;
                }

                String updateTripSql =
                        "UPDATE trips "
                      + "SET trip_end_datetime = NOW(), "
                      + "trip_status = 'COMPLETED' "
                      + "WHERE trip_id = ?";

                try (PreparedStatement ps =
                             con.prepareStatement(
                                     updateTripSql)) {

                    ps.setInt(1, tripId);

                    ps.executeUpdate();
                }

                // Booking completed

                String updateBookingSql =
                        "UPDATE bookings "
                      + "SET booking_status = 'COMPLETED' "
                      + "WHERE booking_id = ?";

                try (PreparedStatement ps =
                             con.prepareStatement(
                                     updateBookingSql)) {

                    ps.setInt(1, bookingId);

                    ps.executeUpdate();
                }

                // -------------------------
                // Release Boat
                // -------------------------

                String boatSql =
                        "UPDATE boats "
                      + "SET status = 'AVAILABLE' "
                      + "WHERE boat_id = "
                      + "(SELECT boat_id FROM bookings "
                      + "WHERE booking_id = ?)";

                try (PreparedStatement ps =
                             con.prepareStatement(boatSql)) {

                    ps.setInt(1, bookingId);

                    ps.executeUpdate();
                }

                out.print("{");
                out.print("\"success\":true,");
                out.print("\"message\":\"Trip completed successfully.\",");
                out.print("\"tripId\":" + tripId + ",");
                out.print("\"bookingId\":" + bookingId + ",");
                out.print("\"tripStatus\":\"COMPLETED\"");
                out.print("}");

                return;
            }

            // =========================
            // INVALID ACTION
            // =========================

            out.print("""
                {
                    "success": false,
                    "message": "Invalid action. Use START or COMPLETE."
                }
                """);

        } catch (Exception e) {

            e.printStackTrace();

            out.print("""
                {
                    "success": false,
                    "message": "Server error while processing trip."
                }
                """);
        }

        out.flush();
    }
}